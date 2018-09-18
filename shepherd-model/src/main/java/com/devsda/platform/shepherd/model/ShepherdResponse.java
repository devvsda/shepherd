package com.devsda.platform.shepherd.model;

import com.devsda.platform.shepherd.constants.ResourceName;
import com.devsda.platform.shepherd.util.DateUtil;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.Map;

public class ShepherdResponse {

    @JsonProperty("resource_name")
    private ResourceName resourceName;

    @JsonProperty("response_time")
    private Date responseTime;

    @JsonProperty("server_name")
    private String serverName;

    @JsonProperty("message")
    private String message;

    @JsonProperty("error_message")
    private String errorMessage;

    @JsonProperty("response_data")
    private Map<String, Object> responseData;

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

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Map<String, Object> getResponseData() {
        return responseData;
    }

    public void setResponseData(Map<String, Object> responseData) {
        this.responseData = responseData;
    }

    @Override
    public String toString() {
        return "ShepherdResponse{" +
                "resourceName=" + resourceName +
                ", responseTime=" + responseTime +
                ", serverName='" + serverName + '\'' +
                ", message='" + message + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", responseData=" + responseData +
                '}';
    }
}
