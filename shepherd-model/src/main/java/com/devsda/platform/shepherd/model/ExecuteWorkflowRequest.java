package com.devsda.platform.shepherd.model;

import com.devsda.platform.shepherd.constants.ResourceName;
import com.devsda.platform.shepherd.constants.WorkflowExecutionState;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExecuteWorkflowRequest extends ShepherdRequest {

    @JsonProperty("execution_id")
    private Integer executionId;

    @JsonProperty("client_name")
    private String clientName;

    @JsonProperty("client_id")
    private Integer clientId;

    @JsonProperty("endpoint_name")
    private String endpointName;

    @JsonProperty("endpoint_id")
    private Integer endpointId;

    @JsonProperty("status")
    private WorkflowExecutionState workflowExecutionState;

    @JsonProperty("initial_payload")
    private Map<String, Object> initialPayload;

    public ExecuteWorkflowRequest() {
        super(ResourceName.EXECUTE_WORKFLOW);
        this.workflowExecutionState = WorkflowExecutionState.PENDING;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getEndpointName() {
        return endpointName;
    }

    public void setEndpointName(String endpointName) {
        this.endpointName = endpointName;
    }

    public Integer getEndpointId() {
        return endpointId;
    }

    public void setEndpointId(Integer endpointId) {
        this.endpointId = endpointId;
    }

    public WorkflowExecutionState getWorkflowExecutionState() {
        return workflowExecutionState;
    }

    public void setWorkflowExecutionState(WorkflowExecutionState workflowExecutionState) {
        this.workflowExecutionState = workflowExecutionState;
    }

    public Map<String, Object> getInitialPayload() {
        return initialPayload;
    }

    public void setInitialPayload(Map<String, Object> initialPayload) {
        this.initialPayload = initialPayload;
    }

    public Integer getExecutionId() {
        return executionId;
    }

    public void setExecutionId(Integer executionId) {
        this.executionId = executionId;
    }

    @Override
    public String toString() {
        return "ExecuteWorkflowRequest{" +
                "executionId=" + executionId +
                ", clientName='" + clientName + '\'' +
                ", clientId=" + clientId +
                ", endpointName='" + endpointName + '\'' +
                ", endpointId=" + endpointId +
                ", workflowExecutionState=" + workflowExecutionState +
                ", initialPayload=" + initialPayload +
                '}';
    }
}
