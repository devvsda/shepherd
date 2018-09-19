package com.devsda.platform.shepherd.model;

import com.devsda.platform.shepherd.constants.NodeState;

import java.util.List;

public class Node {

    private String name;
    private List<Connection> connections;
    private List<Node> parentNodes;
    private String owner;
    private NodeState nodeState = NodeState.NOT_PROCESSED;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public void setConnections(List<Connection> connections) {
        this.connections = connections;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public NodeState getNodeState() {
        return nodeState;
    }

    public void setNodeState(NodeState nodeState) {
        this.nodeState = nodeState;
    }

    public List<Node> getParentNodes() {
        return parentNodes;
    }

    public void setParentNodes(List<Node> parentNodes) {
        this.parentNodes = parentNodes;
    }

    @Override
    public String toString() {
        return "Node{" +
                "name='" + name + '\'' +
                ", connections=" + connections +
                ", parentNodes=" + parentNodes +
                ", owner='" + owner + '\'' +
                ", nodeState=" + nodeState +
                '}';
    }
}
