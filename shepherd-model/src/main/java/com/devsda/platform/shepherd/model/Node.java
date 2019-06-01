package com.devsda.platform.shepherd.model;

import com.devsda.platform.shepherd.constants.GraphType;
import com.devsda.platform.shepherd.constants.NodeState;
import com.devsda.platform.shepherd.constants.ResourceName;

import java.util.List;

public class Node extends ShepherdRequest {

    private String name;

    private String objectId;
    private String executionId;

    private GraphType graphType;

    private List<Connection> connections;
    private List<String> parentNodes;
    private NodeConfiguration nodeConfiguration;

    private String owner;
    private NodeState nodeState;

    public Node() {
        super(ResourceName.EXECUTE_WORKFLOW);
        this.nodeState = NodeState.NOT_PROCESSED;
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

    public List<String> getParentNodes() {
        return parentNodes;
    }

    public void setParentNodes(List<String> parentNodes) {
        this.parentNodes = parentNodes;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getExecutionId() {
        return executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    public NodeConfiguration getNodeConfiguration() {
        return nodeConfiguration;
    }

    public void setNodeConfiguration(NodeConfiguration nodeConfiguration) {
        this.nodeConfiguration = nodeConfiguration;
    }

    public GraphType getGraphType() {
        return graphType;
    }

    public void setGraphType(GraphType graphType) {
        this.graphType = graphType;
    }


    @Override
    public String toString() {
        return "Node{" +
                "name='" + name + '\'' +
                ", objectId='" + objectId + '\'' +
                ", executionId='" + executionId + '\'' +
                ", graphType=" + graphType +
                ", connections=" + connections +
                ", parentNodes=" + parentNodes +
                ", nodeConfiguration=" + nodeConfiguration +
                ", owner='" + owner + '\'' +
                ", nodeState=" + nodeState +
                '}';
    }
}
