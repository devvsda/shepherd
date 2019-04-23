package com.devsda.platform.shephardcore.service;

import com.devsda.platform.shephardcore.dao.RegisterationDao;
import com.devsda.platform.shephardcore.dao.WorkflowOperationDao;
import com.devsda.platform.shepherd.constants.NodeState;
import com.devsda.platform.shepherd.exception.ClientInvalidRequestException;
import com.devsda.platform.shepherd.graphgenerator.DAGGenerator;
import com.devsda.platform.shephardcore.loader.JSONLoader;
import com.devsda.platform.shephardcore.model.*;
import com.devsda.platform.shephardcore.util.GraphUtil;
import com.devsda.platform.shepherd.model.*;
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

        // Get ClientId , and EndpointId.

        // Get endpoint details.
        EndpointDetails endpointDetails = registerationDao.getEndpointDetails(executeWorkflowRequest.getClientName(), executeWorkflowRequest.getEndpointName());

        if(endpointDetails == null) {
            log.error(String.format("Client + Endpoint combination not registered. ClientName : %s. EndpointName : %s",
                    executeWorkflowRequest.getClientName(), executeWorkflowRequest.getEndpointName()));
            throw new ClientInvalidRequestException(String.format("Client + Endpoint combination not registered." +
                    " ClientName : %s. EndpointName : %s", executeWorkflowRequest.getClientName(), executeWorkflowRequest.getEndpointName()));
        }

        executeWorkflowRequest.setClientId(endpointDetails.getClientId());
        executeWorkflowRequest.setEndpointId(endpointDetails.getEndpointId());


        // Generate graph details, and load graph configurations.
        DAGGenerator dagGenerator = new DAGGenerator();
        Graph graph = dagGenerator.generateFromString(endpointDetails.getDAGGraph());
        GraphConfiguration graphConfiguration = JSONLoader.loadFromStringifiedObject(endpointDetails.getEndpointDetails(), GraphConfiguration.class);

        log.debug(String.format("Graph : %s. GraphConfiguration : %s", graph, graphConfiguration));

        Integer executionId = workflowOperationDao.executeWorkflow(executeWorkflowRequest);
        executeWorkflowRequest.setExecutionId(executionId);

        // Create document for given executionId.
        // TODO : Store initial payload in documentDb corresponding to executionId.

        ExecuteWorkflowRunner executeWorkflowRunner = new ExecuteWorkflowRunner(graph, graphConfiguration, executeWorkflowRequest);
        executeWorkflowRunner.call();

        return executionId;
    }
}
