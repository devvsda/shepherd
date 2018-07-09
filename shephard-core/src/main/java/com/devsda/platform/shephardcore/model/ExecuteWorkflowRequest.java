package com.devsda.platform.shephardcore.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class ExecuteWorkflowRequest {

    @JsonProperty("clientName")
    private String clientName;

    @JsonProperty("endpointName")
    private String endpointName;

    @JsonProperty("initialPayload")
    private Map<String, Object> initialPayload;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getEndpointName() {
        return endpointName;
    }

    public void setEndpointName(String endpointName) {
        this.endpointName = endpointName;
    }

    public Map<String, Object> getInitialPayload() {
        return initialPayload;
    }

    public void setInitialPayload(Map<String, Object> initialPayload) {
        this.initialPayload = initialPayload;
    }

    @Override
    public String toString() {
        return "ExecuteWorkflowRequest{" +
                "clientName='" + clientName + '\'' +
                ", endpointName='" + endpointName + '\'' +
                ", initialPayload=" + initialPayload +
                '}';
    }
}
