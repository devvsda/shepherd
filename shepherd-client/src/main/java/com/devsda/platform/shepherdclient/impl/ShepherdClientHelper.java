package com.devsda.platform.shepherdclient.impl;

import com.devsda.platform.shepherd.model.ExecuteWorkflowRequest;
import com.devsda.platform.shepherd.model.RegisterClientRequest;
import com.devsda.platform.shepherd.model.RegisterEndpointRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class ShepherdClientHelper {

    private static final Logger log = LoggerFactory.getLogger(ShepherdClientHelper.class);

    public RegisterClientRequest createRegisterClientRequest(String clientName) {

        log.debug(String.format("Creating ShepherdRequest for client : %s", clientName));

        RegisterClientRequest registerClientRequest = new RegisterClientRequest();
        registerClientRequest.setClientName(clientName);
        return registerClientRequest;
    }

    public RegisterEndpointRequest createRegisterEndpointRequest(String clientName, String endpointName, String graphData, String endpointDetails) {
        RegisterEndpointRequest registerEndpointRequest = new RegisterEndpointRequest();
        registerEndpointRequest.setClientName(clientName);
        registerEndpointRequest.setEndpointName(endpointName);
        registerEndpointRequest.setDAGGraph(graphData);
        registerEndpointRequest.setEndpointDetails(endpointDetails);
        return registerEndpointRequest;
    }

    public ExecuteWorkflowRequest createExecuteWorkflowRequest(String clientName, String endpointName, Map<String, Object> initialObject) {

        ExecuteWorkflowRequest executeWorkflowRequest = new ExecuteWorkflowRequest();

        executeWorkflowRequest.setClientName(clientName);
        executeWorkflowRequest.setEndpointName(endpointName);
        executeWorkflowRequest.setInitialPayload(initialObject);
        return executeWorkflowRequest;
    }
}
