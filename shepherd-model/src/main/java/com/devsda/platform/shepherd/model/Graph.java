package com.devsda.platform.shepherd.model;

import com.devsda.platform.shepherd.constants.GraphType;

import java.util.List;

public class Graph {

    private GraphType graphType;

    private List<Node> nodes;

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public GraphType getGraphType() {
        return graphType;
    }

    public void setGraphType(GraphType graphType) {
        this.graphType = graphType;
    }

    @Override
    public String toString() {
        return "Graph{" +
                "graphType=" + graphType +
                ", nodes=" + nodes +
                '}';
    }
}
