package com.devsda.platform.shepherdcore.service;

import com.devsda.platform.shepherdcore.dao.RegisterationDao;
import com.devsda.platform.shepherdcore.dao.WorkflowOperationDao;
import com.devsda.platform.shepherdcore.loader.JSONLoader;
import com.devsda.platform.shepherdcore.service.documentservice.ExecutionDocumentService;
import com.devsda.platform.shepherdcore.util.UUIDUtil;
import com.devsda.platform.shepherd.constants.ResourceName;
import com.devsda.platform.shepherd.constants.ShepherdConstants;
import com.devsda.platform.shepherd.constants.WorkflowExecutionState;
import com.devsda.platform.shepherd.exception.ClientInvalidRequestException;
import com.devsda.platform.shepherd.graphgenerator.DAGGenerator;
import com.devsda.platform.shepherd.model.*;
import com.devsda.platform.shepherd.util.DateUtil;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.FutureTask;

public class ExecuteWorkflowService {

    private static final Logger log = LoggerFactory.getLogger(ExecuteWorkflowService.class);

    @Inject
    private RegisterationDao registerationDao;

    @Inject
    private WorkflowOperationDao workflowOperationDao;

    @Inject
    private ExecutionDocumentService executionDocumentService;

    @Inject
    private ExecuteWorkflowServiceHelper executeWorkflowServiceHelper;

    /**
     * This method helps to execute workflow.
     *
     * @param executeWorkflowRequest ExecuteWorkflow request from client.
     */
    public Map<String, Object> executeWorkflow(ExecuteWorkflowRequest executeWorkflowRequest) throws Exception {


        // validate.
        // Get ClientId , and EndpointId.
        ClientDetails clientDetails = registerationDao.getClientDetails(executeWorkflowRequest.getClientName());

        if (clientDetails == null) {
            throw new ClientInvalidRequestException(String.format("ClientName : %s does not exists.", executeWorkflowRequest.getClientName()));
        }

        // Get endpoint details.
        EndpointDetails endpointDetails = registerationDao.getEndpointDetails(clientDetails.getClientId(), executeWorkflowRequest.getEndpointName());

        if (endpointDetails == null) {
            log.error(String.format("Endpoint : %s is not present for client : %s", endpointDetails.getEndpointName(), endpointDetails.getClientName()));
            throw new ClientInvalidRequestException(String.format("Client + Endpoint combination not registered." +
                    " ClientName : %s. EndpointName : %s", executeWorkflowRequest.getClientName(), executeWorkflowRequest.getEndpointName()));
        }

        executeWorkflowRequest.setClientId(endpointDetails.getClientId());
        executeWorkflowRequest.setEndpointId(endpointDetails.getEndpointId());

        if(ResourceName.EXECUTE_WORKFLOW.equals(executeWorkflowRequest.getResourceName())) {
            executeWorkflowRequest.setObjectId(UUIDUtil.UUIDGenerator());
        }

        executeWorkflowRequest.setExecutionId(UUIDUtil.UUIDGenerator());
        executeWorkflowRequest.setWorkflowExecutionState(WorkflowExecutionState.PROCESSING);
        executeWorkflowRequest.setUpdatedAt(DateUtil.currentDate());
        executeWorkflowRequest.setSubmittedBy(ShepherdConstants.PROCESS_OWNER);

        // Create entry in execution_details table.
        workflowOperationDao.executeWorkflow(executeWorkflowRequest);

        executionDocumentService.insertExecutionDetails(executeWorkflowRequest, executeWorkflowRequest.getInitialPayload());

        // Generate graph details, and load graph configurations.
        DAGGenerator dagGenerator = new DAGGenerator();
        Graph graph = dagGenerator.generateFromString(endpointDetails.getDAGGraph());
        GraphConfiguration graphConfiguration = JSONLoader.loadFromStringifiedObject(endpointDetails.getEndpointDetails(), GraphConfiguration.class);

        log.debug(String.format("Graph : %s. GraphConfiguration : %s", graph, graphConfiguration));

        // TODO : 1. Need to push global details from json file to Redis.
        // TODO : 2. Need to push Node details to Redis.
        // TODO : 3. Push root node message to RabbitMQ.

        // ExecuteWorkflowRunner executeWorkflowRunner = new ExecuteWorkflowRunner(graph, graphConfiguration, executeWorkflowRequest);

        executeWorkflowServiceHelper.triggerExecution(executeWorkflowRequest, graph, graphConfiguration);
//
//        FutureTask<Void> futureTask = new FutureTask<>(executeWorkflowRunner);
//        Thread t=new Thread(futureTask);
//        t.start();


        Map<String, Object> executionWorkflowResponse = new HashMap<>();
        executionWorkflowResponse.put(ShepherdConstants.OBJECT_ID, executeWorkflowRequest.getObjectId());
        executionWorkflowResponse.put(ShepherdConstants.EXECUTION_ID, executeWorkflowRequest.getExecutionId());
        return executionWorkflowResponse;
    }
}
