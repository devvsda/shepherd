package com.devsda.platform.shepherd.model;

import com.devsda.platform.shepherd.constants.ResourceName;
import com.fasterxml.jackson.annotation.JsonProperty;

public class WorkflowManagementRequest extends ShepherdRequest {

    @JsonProperty("objectId")
    private String objectId;

    @JsonProperty("executionId")
    private String executionId;

    @JsonProperty("clientName")
    private String clientName;

    @JsonProperty("endpointName")
    private String endpointName;

    public WorkflowManagementRequest() {
        super(null);
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getExecutionId() {
        return executionId;
    }

    public void setExecutionId(String executionId) {
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
                "objectId='" + objectId + '\'' +
                ", executionId='" + executionId + '\'' +
                ", clientName='" + clientName + '\'' +
                ", endpointName='" + endpointName + '\'' +
                '}';
    }
}
