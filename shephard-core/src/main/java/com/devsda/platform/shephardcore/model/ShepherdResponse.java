package com.devsda.platform.shephardcore.model;

import com.devsda.platform.shephardcore.constants.ResourceName;

public class ShepherdResponse {

    private ResourceName APIName;
    private Object message;
    private String errorMessage;


    public ResourceName getAPIName() {
        return APIName;
    }

    public void setAPIName(ResourceName APIName) {
        this.APIName = APIName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ShepherdResponse{" +
                "APIName=" + APIName +
                ", message='" + message + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
