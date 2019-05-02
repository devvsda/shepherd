package com.devsda.platform.shepherd.model;

import com.devsda.platform.shepherd.constants.ResourceName;
import com.fasterxml.jackson.annotation.JsonProperty;

public class WorkflowManagementRequest extends ShepherdRequest {

    @JsonProperty("executionId")
    private Integer executionId;

    @JsonProperty("clientName")
    private String clientName;

    @JsonProperty("endpointName")
    private String endpointName;

    public WorkflowManagementRequest(ResourceName resourceName) {
        super(resourceName);
    }

    public Integer getExecutionId() {
        return executionId;
    }

    public void setExecutionId(Integer executionId) {
        this.executionId = executionId;
    }

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

    @Override
    public String toString() {
        return "WorkflowManagementRequest{" +
                "executionId=" + executionId +
                ", clientName='" + clientName + '\'' +
                ", endpointName='" + endpointName + '\'' +
                '}';
    }
}
