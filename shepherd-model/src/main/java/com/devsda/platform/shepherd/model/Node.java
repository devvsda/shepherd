package com.devsda.platform.shepherd.model;

import com.devsda.platform.shepherd.constants.NodeState;
import com.devsda.platform.shepherd.constants.ResourceName;

import java.util.List;

public class Node extends ShepherdRequest {

    private Integer nodeId;
    private String name;

    private Integer executionId;

    private List<Connection> connections;
    private List<Node> parentNodes;
    private String owner;
    private NodeState nodeState = NodeState.NOT_PROCESSED;

    public Node() {
        super(ResourceName.EXECUTE_WORKFLOW);
    }

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

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public Integer getExecutionId() {
        return executionId;
    }

    public void setExecutionId(Integer executionId) {
        this.executionId = executionId;
    }

    @Override
    public String toString() {
        return "Node{" +
                "nodeId=" + nodeId +
                ", name='" + name + '\'' +
                ", executionId=" + executionId +
                ", connections=" + connections +
                ", parentNodes=" + parentNodes +
                ", owner='" + owner + '\'' +
                ", nodeState=" + nodeState +
                '}';
    }
}
