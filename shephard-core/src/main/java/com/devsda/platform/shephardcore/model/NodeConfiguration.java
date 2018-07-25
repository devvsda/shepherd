package com.devsda.platform.shephardcore.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class NodeConfiguration {

    @JsonProperty("name")
    private String name;

    @JsonProperty("URI")
    private String URI;

    @JsonProperty("httpMethod")
    private String httpMethod;

    @JsonProperty("headers")
    private Map<String, String> headers;

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

    @Override
    public String toString() {
        return "NodeConfiguration{" +
                "name='" + name + '\'' +
                ", URI='" + URI + '\'' +
                ", httpMethod='" + httpMethod + '\'' +
                ", headers=" + headers +
                '}';
    }
}
