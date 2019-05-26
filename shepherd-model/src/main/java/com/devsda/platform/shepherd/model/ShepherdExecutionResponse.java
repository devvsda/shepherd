package com.devsda.platform.shepherd.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ShepherdExecutionResponse {

    @JsonProperty("edge")
    private String responseEdge;

    @JsonProperty("executionData")
    private String executionData;

    public ShepherdExecutionResponse(String responseEdge, String executionData) {
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

    public String getExecutionData() {
        return executionData;
    }

    public void setExecutionData(String executionData) {
        this.executionData = executionData;
    }

    @Override
    public String toString() {
        return "ShepherdExecutionResponse{" +
                "responseEdge='" + responseEdge + '\'' +
                ", executionData=" + executionData +
                '}';
    }
}
