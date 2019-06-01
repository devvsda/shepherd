package com.devsda.platform.shepherd.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NodeConfiguration {

    @JsonProperty("name")
    private String name;

    @JsonProperty("URI")
    private String URI;

    @JsonProperty("httpMethod")
    private String httpMethod;

    @JsonProperty("headers")
    private Map<String, String> headers;

    @JsonProperty("serverDetails")
    private ServerDetails serverDetails;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public ServerDetails getServerDetails() {
        return serverDetails;
    }

    public void setServerDetails(ServerDetails serverDetails) {
        this.serverDetails = serverDetails;
    }

    @Override
    public String toString() {
        return "NodeConfiguration{" +
                "name='" + name + '\'' +
                ", URI='" + URI + '\'' +
                ", httpMethod='" + httpMethod + '\'' +
                ", headers=" + headers +
                ", serverDetails=" + serverDetails +
                '}';
    }
}
