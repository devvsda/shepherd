package com.devsda.platform.shephardcore.model;

import java.util.List;

public class Node {

    private String name;
    private List<Connection> connections;
    private String hostname;

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

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    @Override
    public String toString() {
        return "Node{" +
                "name='" + name + '\'' +
                ", connections=" + connections +
                ", hostname='" + hostname + '\'' +
                '}';
    }
}
