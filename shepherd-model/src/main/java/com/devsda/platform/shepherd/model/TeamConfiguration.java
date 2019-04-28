package com.devsda.platform.shepherd.model;

import java.util.List;
import java.util.Map;

public class TeamConfiguration {

    private String owner;

    private ServerDetails serverDetails;

    private Map<String, String> headers;

    private List<NodeConfiguration> nodeConfigurations;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

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

    public List<NodeConfiguration> getNodeConfigurations() {
        return nodeConfigurations;
    }

    public void setNodeConfigurations(List<NodeConfiguration> nodeConfigurations) {
        this.nodeConfigurations = nodeConfigurations;
    }

    @Override
    public String toString() {
        return "TeamConfiguration{" +
                "owner='" + owner + '\'' +
                ", serverDetails=" + serverDetails +
                ", headers=" + headers +
                ", nodeConfigurations=" + nodeConfigurations +
                '}';
    }
}
