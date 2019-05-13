package com.devsda.platform.shepherd.model;


import com.devsda.platform.shepherd.constants.WorkflowExecutionState;
import com.devsda.platform.shepherd.model.Node;

import java.util.Date;
import java.util.List;


public class ExecutionDetails {


    private String objectId;
    private String executionId;
    private Integer clientId;
    private Integer endpointId;
    private WorkflowExecutionState workflowExecutionState;
    private String errorMessage;
    private List<Node> nodes;
    private Date createdAt;
    private Date updatedAt;
    private String createdBy;

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

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
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

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    @Override
    public String toString() {
        return "ExecutionDetails{" +
                "objectId='" + objectId + '\'' +
                ", executionId='" + executionId + '\'' +
                ", clientId=" + clientId +
                ", endpointId=" + endpointId +
                ", workflowExecutionState=" + workflowExecutionState +
                ", errorMessage='" + errorMessage + '\'' +
                ", nodes=" + nodes +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", createdBy='" + createdBy + '\'' +
                '}';
    }
}
