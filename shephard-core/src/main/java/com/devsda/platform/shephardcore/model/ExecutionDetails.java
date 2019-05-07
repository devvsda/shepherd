package com.devsda.platform.shephardcore.model;


import com.devsda.platform.shepherd.constants.WorkflowExecutionState;
import com.devsda.platform.shepherd.model.Node;

import java.util.Date;
import java.util.List;


public class ExecutionDetails {


    private Integer executionId;
    private Integer clientId;
    private Integer endpointId;
    private WorkflowExecutionState workflowExecutionState;
    private String errorMessage;
    private List<Node> processedNodes;
    private List<Node> procressingNodes;
    private Date createdAt;
    private Date updatedAt;
    private String createdBy;

    public Integer getExecutionId() {
        return executionId;
    }

    public void setExecutionId(Integer executionId) {
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

    public List<Node> getProcessedNodes() {
        return processedNodes;
    }

    public void setProcessedNodes(List<Node> processedNodes) {
        this.processedNodes = processedNodes;
    }

    public List<Node> getProcressingNodes() {
        return procressingNodes;
    }

    public void setProcressingNodes(List<Node> procressingNodes) {
        this.procressingNodes = procressingNodes;
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

    @Override
    public String toString() {
        return "ExecutionDetails{" +
                "executionId=" + executionId +
                ", clientId=" + clientId +
                ", endpointId=" + endpointId +
                ", workflowExecutionState=" + workflowExecutionState +
                ", errorMessage='" + errorMessage + '\'' +
                ", processedNodes=" + processedNodes +
                ", procressingNodes=" + procressingNodes +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", createdBy='" + createdBy + '\'' +
                '}';
    }
}
