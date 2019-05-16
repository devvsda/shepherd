package com.devsda.platform.shepherd.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;


public class ExecutionData {

    public Map<String, Object> getInitialPayload() {
        return initialPayload;
    }

    public void setInitialPayload(Map<String, Object> initialPayload) {
        this.initialPayload = initialPayload;
    }

    public ExecutionData(Map<String, Object> initialPayload) {
        this.initialPayload = initialPayload;
    }

    @Override
    public String toString() {
        return "ExecutionData{" +
                "initialPayload=" + initialPayload +
                '}';
    }

    public ExecutionData() {
    }
    @JsonProperty("initialPayload")
    private Map<String, Object> initialPayload;

}
