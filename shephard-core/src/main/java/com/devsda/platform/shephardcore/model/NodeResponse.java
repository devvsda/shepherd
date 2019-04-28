package com.devsda.platform.shephardcore.model;

import com.devsda.platform.shepherd.constants.NodeState;

public class NodeResponse {

    private String nodeName;
    private NodeState nodeState;
    private String clientResponse;

    public NodeResponse(String nodeName, NodeState nodeState, String clientResponse) {
        this.nodeName = nodeName;
        this.nodeState = nodeState;
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

    public NodeState getNodeState() {
        return nodeState;
    }

    public void setNodeState(NodeState nodeState) {
        this.nodeState = nodeState;
    }

    @Override
    public String toString() {
        return "NodeResponse{" +
                "nodeName='" + nodeName + '\'' +
                ", nodeState=" + nodeState +
                ", clientResponse='" + clientResponse + '\'' +
                '}';
    }
}
