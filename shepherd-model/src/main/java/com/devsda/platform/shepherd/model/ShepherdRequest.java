package com.devsda.platform.shepherd.model;

import com.devsda.platform.shepherd.constants.ResourceName;
import com.devsda.platform.shepherd.util.DateUtil;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class ShepherdRequest {

    @JsonProperty("created_at")
    private Date createdAt;

    @JsonProperty("updated_at")
    private Date updatedAt;

    @JsonProperty("resource_name")
    private ResourceName resourceName;

    @JsonProperty("submitted_by")
    private String submittedBy;

    public ShepherdRequest(ResourceName resourceName) {
        this.resourceName = resourceName;
        this.createdAt = DateUtil.currentDate();
        this.updatedAt = DateUtil.currentDate();
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
