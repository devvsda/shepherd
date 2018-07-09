package com.devsda.platform.shephardcore.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisterClientRequest {

    @JsonProperty("clientName")
    private String clientName;

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
