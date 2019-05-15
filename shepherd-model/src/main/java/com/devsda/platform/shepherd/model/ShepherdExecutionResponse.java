package com.devsda.platform.shepherd.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ShepherdExecutionResponse {

    @JsonProperty("edge")
    private String responseEdge;

    @JsonProperty("executionData")
    private ExecutionData executionData;

    public ShepherdExecutionResponse(String responseEdge, ExecutionData executionData) {
        this.responseEdge = responseEdge;
        this.executionData = executionData;
    }

    public String getResponseEdge() {
        return responseEdge;
    }

    public void setResponseEdge(String responseEdge) {
        this.responseEdge = responseEdge;
    }

    public ShepherdExecutionResponse() {
    }

    @Override
    public String toString() {
        return "ShepherdExecutionResponse{" +
                "responseEdge='" + responseEdge + '\'' +
                ", executionData=" + executionData +
                '}';
    }

    public ExecutionData getExecutionData() {
        return executionData;
    }

    public void setExecutionData(ExecutionData executionData) {
        this.executionData = executionData;
    }

}
