package com.devsda.platform.shephardcore.model;

public class Connection {

    private String edgeName;
    private String nodeName;

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

    @Override
    public String toString() {
        return "Connection{" +
                "edgeName='" + edgeName + '\'' +
                ", nodeName='" + nodeName + '\'' +
                '}';
    }
}
