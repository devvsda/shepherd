package com.devsda.platform.shepherd.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Connection {

    private String edgeName;
    private String nodeName;
    private Node node;

    public String getEdgeName() {
        return edgeName;
    }

    public void setEdgeName(String edgeName) {
        this.edgeName = edgeName;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    @Override
    public String toString() {
        return "Connection{" +
                "edgeName='" + edgeName + '\'' +
                ", nodeName='" + nodeName + '\'' +
                ", node=" + node +
                '}';
    }
}
