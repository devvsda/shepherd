package com.devsda.platform.shepherd.model;

import com.devsda.platform.shepherd.util.DateUtil;
import org.apache.http.client.utils.DateUtils;

import java.util.Date;

public class ShepherdResponse {

    private Date responseTime;

    private ResourceName resourceName;

    public ShepherdResponse(ResourceName resourceName) {
        this.responseTime = DateUtil.currentDate();
        this.resourceName = resourceName;
    }

    public Date getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Date responseTime) {
        this.responseTime = responseTime;
    }

    public ResourceName getResourceName() {
        return resourceName;
    }

    public void setResourceName(ResourceName resourceName) {
        this.resourceName = resourceName;
    }

    @Override
    public String toString() {
        return "ShepherdResponse{" +
                "responseTime=" + responseTime +
                ", resourceName=" + resourceName +
                '}';
    }
}
