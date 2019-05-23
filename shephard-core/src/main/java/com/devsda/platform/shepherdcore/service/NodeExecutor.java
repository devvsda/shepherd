package com.devsda.platform.shepherdcore.service;

import com.devsda.platform.shepherd.constants.GraphType;
import com.devsda.platform.shepherdcore.constants.ShephardConstants;
import com.devsda.platform.shepherdcore.dao.WorkflowOperationDao;
import com.devsda.platform.shepherd.model.ExecutionDetails;
import com.devsda.platform.shepherdcore.loader.JSONLoader;
import com.devsda.platform.shepherdcore.model.NodeResponse;
import com.devsda.platform.shepherdcore.service.documentservice.ExecutionDocumentService;
import com.devsda.platform.shepherd.constants.NodeState;
import com.devsda.platform.shepherd.constants.ShepherdConstants;
import com.devsda.platform.shepherd.constants.WorkflowExecutionState;
import com.devsda.platform.shepherd.exception.ClientNodeFailureException;
import com.devsda.platform.shepherd.exception.NodeFailureException;
import com.devsda.platform.shepherd.model.*;
import com.devsda.platform.shepherd.util.DateUtil;
import com.devsda.platform.shepherdcore.service.queueservice.RabbitMQOperation;
import com.devsda.utils.httputils.methods.HttpPostMethod;
import com.google.inject.Inject;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import org.apache.http.client.HttpResponseException;
import org.apache.http.entity.StringEntity;
import org.bson.Document;
import org.skife.jdbi.v2.exceptions.UnableToExecuteStatementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import java.util.List;

public class NodeExecutor {

    private static final Logger log = LoggerFactory.getLogger(NodeExecutor.class);

    @Named(ShephardConstants.RabbitMQ.PUBLISHER)
    @Inject
    private com.rabbitmq.client.Connection publisherConnection;

    @Inject
    private WorkflowOperationDao workflowOperationDao;

    @Inject
    private ExecutionDocumentService executionDocumentService;

    @Inject
    private ExecuteWorkflowServiceHelper executeWorkflowServiceHelper;

    @Inject
    private RabbitMQOperation rabbitMQOperation;

    public NodeResponse execute(Node node) throws Exception {

        String response = null;
        NodeConfiguration nodeConfiguration = node.getNodeConfiguration();
        ServerDetails serverDetails = nodeConfiguration.getServerDetails();

        log.info(String.format("Executing node : %s. NodeConfiguration : %s", nodeConfiguration.getName(), nodeConfiguration));

        try {

            Boolean isExecutionInKilledState = isExecutionInKilledState(node);

            if (isExecutionInKilledState) {
                NodeResponse nodeResponse = killThisNode(node);
                return nodeResponse;
            }

            // Create node with PROCESSING state.
            createNodeInDB(node);

            ShepherdExecutionResponse clientNodeResponse = null;

            // Fetch document of this execution.
            Document executionDetailsDoc = executionDocumentService.fetchExecutionDetails(node.getObjectId(), node.getExecutionId());
            String executionDataJson= ((Document)executionDetailsDoc.get("executionData")).toJson();

            // Execute Node.
            HttpPostMethod clientNodeRequest= new HttpPostMethod();
            clientNodeResponse = clientNodeRequest.call(serverDetails.getProtocol(), serverDetails.getHostName(), serverDetails.getPort(),
                    nodeConfiguration.getURI(), null , nodeConfiguration.getHeaders(),
                    new StringEntity(executionDataJson), ShepherdExecutionResponse.class);

            boolean isExecutionDocumentUpdated = executionDocumentService.updateExecutionDetails(node.getObjectId(), node.getExecutionId(), clientNodeResponse.getExecutionData());

            log.info(String.format("Response of Node : %s is %s, isExecutionDocumentUpdated %s", nodeConfiguration.getName(), clientNodeResponse.getResponseEdge(),isExecutionDocumentUpdated));

            // Update Node status as Completed in Node table.
            updateNodeStatus(node, NodeState.COMPLETED);

            pushChildrenNodesToRabbitMQ(clientNodeResponse.getResponseEdge(), node);

            updateExecutionStatus(node, WorkflowExecutionState.COMPLETED, null);

            return new NodeResponse(nodeConfiguration.getName(), NodeState.COMPLETED, response);

        } catch(UnableToExecuteStatementException e) {
            // This exception occurs in UNCONDITIONAL workflow. When we try to execute same node twice.
            log.info(String.format("Node : %s already under processing. Skipping this execution to avoid duplicity.",
                    nodeConfiguration.getName()), e);
            updateExecutionStatus(node, WorkflowExecutionState.FAILED, e.getMessage());
            return new NodeResponse(nodeConfiguration.getName(), NodeState.SKIPPED, null);

        } catch (HttpResponseException e) {

            log.error(String.format("Node : %s failed at client side.", nodeConfiguration.getName()), e);
            node.setErrorMessage(e.getMessage());
            updateNodeStatus(node, NodeState.FAILED);
            updateExecutionStatus(node, WorkflowExecutionState.FAILED, e.getMessage());
            throw new ClientNodeFailureException(String.format("Node : %s failed at client side.", nodeConfiguration.getName()), e);

        } catch (Exception e) {

            log.error(String.format("Node : %s failed internally.", nodeConfiguration.getName()), e);
            node.setErrorMessage(e.getMessage());
            updateNodeStatus(node, NodeState.FAILED);
            updateExecutionStatus(node, WorkflowExecutionState.FAILED, e.getMessage());
            throw new NodeFailureException(String.format("Node : %s failed internally.", nodeConfiguration.getName()), e);

        }
    }

    private void pushChildrenNodesToRabbitMQ(String edgeName, Node node) throws Exception {

        Boolean isGraphConditional = GraphType.CONDITIONAL.equals(node.getGraphType());

        if (node.getConnections() == null) {
            return;
        }

        if(isGraphConditional) {

            for(Connection connection :  node.getConnections()) {

                if(connection.getEdgeName().equalsIgnoreCase(edgeName)) {
                    Channel channel = rabbitMQOperation.createChannel(publisherConnection);
                    rabbitMQOperation.decalareExchangeAndBindQueue(channel,"shepherd_exchange","first-queue","routingKey", BuiltinExchangeType.DIRECT,true,6000);
                    rabbitMQOperation.publishMessage(channel, "shepherd_exchange", "routingKey", JSONLoader.stringify(connection.getNode()));
                    return;
                }
            }
        } else {

            for (Connection connection :  node.getConnections()) {

                Boolean isChildNodeReadyToExecute = executeWorkflowServiceHelper.isNodeReadyToExecute(connection.getNode());

                if(isChildNodeReadyToExecute) {
                    Channel channel = rabbitMQOperation.createChannel(publisherConnection);
                    rabbitMQOperation.decalareExchangeAndBindQueue(channel,"shepherd_exchange","first-queue","routingKey", BuiltinExchangeType.DIRECT,true,6000);
                    rabbitMQOperation.publishMessage(channel, "shepherd_exchange", "routingKey", JSONLoader.stringify(connection.getNode()));
                } else {
                    // TODO : Push to secondary Queue.
                }
            }
        }
    }

    private Boolean isExecutionInKilledState(Node node) {

        // Check weather execution is killed by customer or not. If yes, then no need to execute this node.
        ExecutionDetails executionDetails = workflowOperationDao.getExecutionDetails( node.getObjectId(),  node.getExecutionId());

        if ( WorkflowExecutionState.KILLED.equals(executionDetails.getWorkflowExecutionState()) ) {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    private NodeResponse killThisNode(Node node) {
        log.info(String.format("Execution id : %s is in killed state. Skipping execution of ndoe : %s",
                 node.getExecutionId(),  node.getName()));
         node.setUpdatedAt(DateUtil.currentDate());
         node.setNodeState(NodeState.KILLED);
         node.setSubmittedBy(ShepherdConstants.PROCESS_OWNER);
        workflowOperationDao.createNode( node);
        return new NodeResponse( node.getName(), NodeState.KILLED, null);
    }

    private void createNodeInDB(Node node) {
         node.setUpdatedAt(DateUtil.currentDate());
         node.setNodeState(NodeState.PROCESSING);
         node.setSubmittedBy(ShepherdConstants.PROCESS_OWNER);
        workflowOperationDao.createNode( node);
    }

    private void updateNodeStatus(Node node, NodeState nodeState) {
         node.setNodeState(nodeState);
         node.setUpdatedAt(DateUtil.currentDate());
        workflowOperationDao.updateNode(node);
    }

    public void updateExecutionStatus(Node node, WorkflowExecutionState workflowExecutionState, String errorMessage) {

        List<Connection> childernNodes = node.getConnections();

        if (GraphType.CONDITIONAL.equals(node.getGraphType()) &&
                (childernNodes == null || childernNodes.isEmpty())) {
            workflowOperationDao.updateExecutionStatus(node.getObjectId(), node.getExecutionId(), workflowExecutionState, errorMessage);
            return;
        }

        // TODO: Implement UNCONDITIONAL workflow logic.
    }
}
