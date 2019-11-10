package com.devsda.platform.shepherdcore.service;

import com.devsda.platform.shepherd.constants.NodeState;
import com.devsda.platform.shepherd.graphgenerator.DAGGenerator;
import com.devsda.platform.shepherd.model.*;
import com.devsda.platform.shepherd.util.DateUtil;
import com.devsda.platform.shepherdcore.constants.ShephardConstants;
import com.devsda.platform.shepherdcore.dao.WorkflowOperationDao;
import com.devsda.platform.shepherdcore.loader.JSONLoader;
import com.devsda.platform.shepherdcore.service.queueservice.RabbitMQOperation;
import com.devsda.platform.shepherdcore.util.GraphUtil;
import com.devsda.platform.shepherdcore.util.RequestValidator;
import com.devsda.platform.shepherd.constants.ResourceName;
import com.devsda.platform.shepherd.constants.WorkflowExecutionState;
import com.google.inject.Inject;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.inject.Named;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkflowManagementService {

    private static final Logger log = LoggerFactory.getLogger(WorkflowOperationDao.class);

    @Named(ShephardConstants.RabbitMQ.PUBLISHER)
    @Inject
    private Connection publisherConnection;

    @Inject
    private WorkflowOperationDao workflowOperationDao;

    @Inject
    private ExecuteWorkflowService executeWorkflowService;

    @Inject
    private RabbitMQOperation rabbitMQOperation;

    public void killWorkflow(WorkflowManagementRequest killWorkflowRequest) {


        ClientDetails clientDetails = RequestValidator.validateClient(killWorkflowRequest.getClientName());
        RequestValidator.validateEndpoint(clientDetails.getClientId(), killWorkflowRequest.getClientName(), killWorkflowRequest.getEndpointName());
        RequestValidator.validateExecution(
                killWorkflowRequest.getClientName(), killWorkflowRequest.getEndpointName(), killWorkflowRequest.getObjectId(), killWorkflowRequest.getExecutionId());

        log.debug(String.format("Updating status of execution id : %s with value : %s", killWorkflowRequest.getExecutionId(), WorkflowExecutionState.KILLED.name()));

        workflowOperationDao.updateExecutionStatus(killWorkflowRequest.getObjectId(), killWorkflowRequest.getExecutionId(), WorkflowExecutionState.KILLED, null);
    }

    public void resumeWorkflow(WorkflowManagementRequest resumeWorkflowRequest) throws ParserConfigurationException, SAXException, IOException {

        ClientDetails clientDetails = RequestValidator.validateClient(resumeWorkflowRequest.getClientName());
        EndpointDetails endpointDetails = RequestValidator.validateEndpoint(clientDetails.getClientId(), resumeWorkflowRequest.getClientName(), resumeWorkflowRequest.getEndpointName());
        ExecutionDetails executionDetails = RequestValidator.validateExecution(
                resumeWorkflowRequest.getClientName(), resumeWorkflowRequest.getEndpointName(), resumeWorkflowRequest.getObjectId(), resumeWorkflowRequest.getExecutionId());


        if (WorkflowExecutionState.COMPLETED.equals(executionDetails) ||
                WorkflowExecutionState.PROCESSING.equals(executionDetails) ||
                WorkflowExecutionState.PENDING.equals(executionDetails)) {
            return;
        }

        // Fetch all processed nodes.
        List<Node> processedNodes = workflowOperationDao.getAllNodes(resumeWorkflowRequest.getObjectId(), resumeWorkflowRequest.getExecutionId());
        List<String> nodesToBeResumed = getNodesToBeResumed(processedNodes);
        pushReadyToExecuteNodesToRabbitMQ(resumeWorkflowRequest.getObjectId(),
                resumeWorkflowRequest.getExecutionId(), endpointDetails, nodesToBeResumed);

        workflowOperationDao.updateExecutionStatus(resumeWorkflowRequest.getObjectId(),
                resumeWorkflowRequest.getExecutionId(), WorkflowExecutionState.PROCESSING, null);
    }

    private void pushReadyToExecuteNodesToRabbitMQ(String objectId, String executionId,
                                                   EndpointDetails endpointDetails, List<String> readyToExecuteNodes) throws ParserConfigurationException,
            SAXException, IOException{

        if (readyToExecuteNodes == null || readyToExecuteNodes.isEmpty()) {
            return;
        }

        DAGGenerator dagGenerator = new DAGGenerator();
        Graph graph = dagGenerator.generateFromString(endpointDetails.getDAGGraph());
        GraphConfiguration graphConfiguration = JSONLoader.loadFromStringifiedObject(endpointDetails.getEndpointDetails(), GraphConfiguration.class);
        Map<String, Node> nodeNameToNodeMapping = GraphUtil.getNodeNameToNodePOJOMapping(
                objectId, executionId,
                graph, graphConfiguration, ResourceName.RESUME_EXECUTION);

        for (String readyToExecuteNode : readyToExecuteNodes) {
            Node readyToExecuteNodeBO = nodeNameToNodeMapping.get(readyToExecuteNode);

            try {

                Channel channel = rabbitMQOperation.createChannel(publisherConnection);
                rabbitMQOperation.decalareExchangeAndBindQueue(channel,"shepherd_exchange",
                        "first_queue","routingKey", BuiltinExchangeType.DIRECT,true,6000);
                rabbitMQOperation.publishMessage(channel, "shepherd_exchange",
                        "routingKey", JSONLoader.stringify(readyToExecuteNodeBO));

            } catch (Exception e) {

                log.error(String.format("Execution : %s failed.", executionId), e);
                workflowOperationDao.updateExecutionStatus(objectId, executionId,
                        WorkflowExecutionState.FAILED, e.getLocalizedMessage());
            }
        }
    }

    private List<String> getNodesToBeResumed(List<Node> nodes) {

        if (nodes == null) {
            return null;
        }

        List<String> nodesToBeResumed = new ArrayList<>();

        for(Node node : nodes) {

            NodeState nodeState = node.getNodeState();

            if(NodeState.FAILED.equals(nodeState) || NodeState.KILLED.equals(nodeState)) {
                nodesToBeResumed.add(node.getName());
            }
        }

        return nodesToBeResumed;
    }

    public Map<String, Object> restartWorkflow(WorkflowManagementRequest restartWorkflowRequest) throws Exception {

        killWorkflow(restartWorkflowRequest);

        ExecuteWorkflowRequest executeWorkflowRequest = new ExecuteWorkflowRequest();
        executeWorkflowRequest.setResourceName(ResourceName.RESTART_EXECUTION);
        executeWorkflowRequest.setClientName(restartWorkflowRequest.getClientName());
        executeWorkflowRequest.setEndpointName(restartWorkflowRequest.getEndpointName());
        executeWorkflowRequest.setInitialPayload("{\"name\": \"hitesh\"}");
        executeWorkflowRequest.setObjectId(restartWorkflowRequest.getObjectId());

        Map<String, Object> restartedExecutionResponse = executeWorkflowService.executeWorkflow(executeWorkflowRequest);

        return restartedExecutionResponse;
    }
}
