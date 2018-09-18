package com.devsda.platform.shepherd.model;

import com.devsda.platform.shepherd.constants.ResourceName;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisterClientRequest extends ShepherdRequest {

    @JsonProperty("clientName")
    private String clientName;

    public RegisterClientRequest() {
        super(ResourceName.REGISTER_CLIENT);
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @Override
    public String toString() {
        return "RegisterClientRequest{" +
                "clientName='" + clientName + '\'' +
                '}';
    }

}
