package com.devsda.platform.shepherd.model;

import java.util.Date;

public class EndpointDetails {

    private Integer endpointId;
    private String endpointName;

    private Integer clientId;
    private String clientName;

    private String DAGGraph;
    private String endpointDetails;

    private Date createdAt;
    private Date updatedAt;
    private String submittedBy;

    public Integer getEndpointId() {
        return endpointId;
    }

    public void setEndpointId(Integer endpointId) {
        this.endpointId = endpointId;
    }

    public String getEndpointName() {
        return endpointName;
    }

    public void setEndpointName(String endpointName) {
        this.endpointName = endpointName;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getDAGGraph() {
        return DAGGraph;
    }

    public void setDAGGraph(String DAGGraph) {
        this.DAGGraph = DAGGraph;
    }

    public String getEndpointDetails() {
        return endpointDetails;
    }

    public void setEndpointDetails(String endpointDetails) {
        this.endpointDetails = endpointDetails;
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

    public String getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(String submittedBy) {
        this.submittedBy = submittedBy;
    }

    @Override
    public String toString() {
        return "EndpointDetails{" +
                "endpointId=" + endpointId +
                ", endpointName='" + endpointName + '\'' +
                ", clientId=" + clientId +
                ", clientName='" + clientName + '\'' +
                ", DAGGraph='" + DAGGraph + '\'' +
                ", endpointDetails='" + endpointDetails + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", submittedBy='" + submittedBy + '\'' +
                '}';
    }
}
