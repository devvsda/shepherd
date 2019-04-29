package com.devsda.platform.shephardcore.model;

import com.devsda.platform.shepherd.util.DateUtil;

import java.util.Date;

public class ClientDetails {

    private Integer clientId;

    private String clientName;

    private Date createdAt;

    private Date updatedAt;

    private String submittedBy;

    public ClientDetails(String clientName, String submittedBy) {
        this.clientName = clientName;
        this.submittedBy = submittedBy;
        this.createdAt = DateUtil.currentDate();
        this.updatedAt = DateUtil.currentDate();
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
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

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return "ClientDetails{" +
                "clientId='" + clientId + '\'' +
                ", clientName='" + clientName + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", submittedBy='" + submittedBy + '\'' +
                '}';
    }

}
