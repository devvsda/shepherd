package com.devsda.platform.shepherdclient.impl;

import com.devsda.platform.shepherd.constants.ResourceName;
import com.devsda.platform.shepherd.constants.WorkflowExecutionState;
import com.devsda.platform.shepherd.model.EndpointRequest;
import com.devsda.platform.shepherd.model.ExecuteWorkflowRequest;
import com.devsda.platform.shepherd.model.RegisterClientRequest;
import com.devsda.platform.shepherd.model.WorkflowManagementRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShepherdClientHelper {

    private static final Logger log = LoggerFactory.getLogger(ShepherdClientHelper.class);

    public RegisterClientRequest createRegisterClientRequest(String clientName) {

        log.debug(String.format("Creating ShepherdRequest for client : %s", clientName));

        RegisterClientRequest registerClientRequest = new RegisterClientRequest();
        registerClientRequest.setClientName(clientName);
        return registerClientRequest;
    }

    public EndpointRequest createEndpointRequest(String clientName, String endpointName, String graphData, String endpointDetails) {
        EndpointRequest registerEndpointRequest = new EndpointRequest();
        registerEndpointRequest.setClientName(clientName);
        registerEndpointRequest.setEndpointName(endpointName);
        registerEndpointRequest.setDAGGraph(graphData);
        registerEndpointRequest.setEndpointDetails(endpointDetails);
        return registerEndpointRequest;
    }

    public ExecuteWorkflowRequest createExecuteWorkflowRequest(String clientName, String endpointName, String initialObject) {

        ExecuteWorkflowRequest executeWorkflowRequest = new ExecuteWorkflowRequest();
        executeWorkflowRequest.setClientName(clientName);
        executeWorkflowRequest.setEndpointName(endpointName);
        executeWorkflowRequest.setInitialPayload(initialObject);
        executeWorkflowRequest.setResourceName(ResourceName.EXECUTE_WORKFLOW);
        return executeWorkflowRequest;
    }

    public WorkflowManagementRequest createWorkflowManagementRequest(String clientName, String endpointName, String objectId, String executionId, ResourceName resourceName) {

        WorkflowManagementRequest workflowManagementRequest = new WorkflowManagementRequest();

        workflowManagementRequest.setClientName(clientName);
        workflowManagementRequest.setEndpointName(endpointName);
        workflowManagementRequest.setObjectId(objectId);
        workflowManagementRequest.setExecutionId(executionId);
        workflowManagementRequest.setResourceName(resourceName);

        return workflowManagementRequest;
    }
}
