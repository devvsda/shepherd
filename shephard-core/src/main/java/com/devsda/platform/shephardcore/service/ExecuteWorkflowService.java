package com.devsda.platform.shephardcore.service;

import com.devsda.platform.shephardcore.dao.RegisterationDao;
import com.devsda.platform.shephardcore.dao.WorkflowOperationDao;
import com.devsda.platform.shepherd.constants.NodeState;
import com.devsda.platform.shepherd.constants.ShepherdConstants;
import com.devsda.platform.shepherd.constants.WorkflowExecutionState;
import com.devsda.platform.shepherd.exception.ClientInvalidRequestException;
import com.devsda.platform.shepherd.graphgenerator.DAGGenerator;
import com.devsda.platform.shephardcore.loader.JSONLoader;
import com.devsda.platform.shephardcore.model.*;
import com.devsda.platform.shephardcore.util.GraphUtil;
import com.devsda.platform.shepherd.model.*;
import com.devsda.platform.shepherd.util.DateUtil;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class ExecuteWorkflowService {

    private static final Logger log = LoggerFactory.getLogger(ExecuteWorkflowService.class);

    @Inject
    private RegisterationDao registerationDao;

    @Inject
    private WorkflowOperationDao workflowOperationDao;


    /**
     * This method helps to execute workflow.
     * @param executeWorkflowRequest ExecuteWorkflow request from client.
     */
    public Integer executeWorkflow(ExecuteWorkflowRequest executeWorkflowRequest) throws Exception {


        // validate.
        // Get ClientId , and EndpointId.
        ClientDetails clientDetails = registerationDao.getClientDetails(executeWorkflowRequest.getClientName());

        if (clientDetails == null) {
            throw new ClientInvalidRequestException(String.format("ClientName : %s does not exists.", executeWorkflowRequest.getClientName()));
        }

        // Get endpoint details.
        EndpointDetails endpointDetails = registerationDao.getEndpointDetails(clientDetails.getClientId(), executeWorkflowRequest.getEndpointName());

        if(endpointDetails == null) {
            log.error(String.format("Endpoint : %s is not present for client : %s", endpointDetails.getEndpointName(), endpointDetails.getClientName()));
            throw new ClientInvalidRequestException(String.format("Client + Endpoint combination not registered." +
                    " ClientName : %s. EndpointName : %s", executeWorkflowRequest.getClientName(), executeWorkflowRequest.getEndpointName()));
        }

        executeWorkflowRequest.setClientId(endpointDetails.getClientId());
        executeWorkflowRequest.setEndpointId(endpointDetails.getEndpointId());

        executeWorkflowRequest.setWorkflowExecutionState(WorkflowExecutionState.PROCESSING);
        executeWorkflowRequest.setUpdatedAt(DateUtil.currentDate());
        executeWorkflowRequest.setSubmittedBy(ShepherdConstants.PROCESS_OWNER);
        Integer executionId = workflowOperationDao.executeWorkflow(executeWorkflowRequest);
        executeWorkflowRequest.setExecutionId(executionId);

        // Generate graph details, and load graph configurations.
        DAGGenerator dagGenerator = new DAGGenerator();
        Graph graph = dagGenerator.generateFromString(endpointDetails.getDAGGraph());
        GraphConfiguration graphConfiguration = JSONLoader.loadFromStringifiedObject(endpointDetails.getEndpointDetails(), GraphConfiguration.class);

        log.debug(String.format("Graph : %s. GraphConfiguration : %s", graph, graphConfiguration));

        // Create document for given executionId.
        // TODO : Store initial payload in documentDb corresponding to executionId.

        ExecuteWorkflowRunner executeWorkflowRunner = new ExecuteWorkflowRunner(graph, graphConfiguration, executeWorkflowRequest);
        executeWorkflowRunner.call();

        return executionId;
    }
}
