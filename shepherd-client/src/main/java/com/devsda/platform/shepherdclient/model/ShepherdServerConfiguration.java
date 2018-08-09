package com.devsda.platform.shepherdclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class ShepherdServerConfiguration {

    @JsonProperty("server")
    private ServerDetails serverDetails;

    @JsonProperty("headers")
    private Map<String, String> headers;

    public ServerDetails getServerDetails() {
        return serverDetails;
    }

    public void setServerDetails(ServerDetails serverDetails) {
        this.serverDetails = serverDetails;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public String toString() {
        return "ShepherdServerConfiguration{" +
                "serverDetails=" + serverDetails +
                ", headers=" + headers +
                '}';
    }
}
