package com.devsda.platform.shephardcore.model;

public class NodeResponse {

    private String nodeName;
    private String clientResponse;

    public NodeResponse(String nodeName, String clientResponse) {
        this.nodeName = nodeName;
        this.clientResponse = clientResponse;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getClientResponse() {
        return clientResponse;
    }

    public void setClientResponse(String clientResponse) {
        this.clientResponse = clientResponse;
    }

    @Override
    public String toString() {
        return "NodeResponse{" +
                "nodeName='" + nodeName + '\'' +
                ", clientResponse='" + clientResponse + '\'' +
                '}';
    }
}
