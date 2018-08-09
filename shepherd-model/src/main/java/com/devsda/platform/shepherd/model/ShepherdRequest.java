package com.devsda.platform.shepherd.model;

import java.util.Date;

public class ShepherdRequest {

    private Date createdAt;

    private Date updatedAt;

    private ResourceName resourceName;

    private String submittedBy;

    public ShepherdRequest(ResourceName resourceName) {
        this.resourceName = resourceName;
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

    public ResourceName getResourceName() {
        return resourceName;
    }

    public void setResourceName(ResourceName resourceName) {
        this.resourceName = resourceName;
    }

    @Override
    public String toString() {
        return "ShepherdRequest{" +
                "createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", resourceName=" + resourceName +
                ", submittedBy='" + submittedBy + '\'' +
                '}';
    }
}
