package com.devsda.platform.shephardcore.util;

import com.devsda.platform.shephardcore.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphUtil {

    public static Map<String, List<String>> getNodeToParentNodeRelation(Graph graph) {

        Map<String, List<String>> nodeToParentNodesRelation = new HashMap<>();

        for(Node node : graph.getNodes()) {
            nodeToParentNodesRelation.put(node.getName(), null);
        }

        for (Node node : graph.getNodes()) {
            if (node.getConnections() == null) continue;

            for (Connection connection : node.getConnections()) {
                String destinationNode = connection.getNodeName();
                List<String> parentNodes = nodeToParentNodesRelation.get(destinationNode);;

                if (parentNodes == null) {
                    parentNodes = new ArrayList<>();

                }

                parentNodes.add(node.getName());
                nodeToParentNodesRelation.put(destinationNode, parentNodes);
            }
        }

        return nodeToParentNodesRelation;
    }

    public static Map<String, Node> nameWiseMapping(Graph graph) {

        Map<String, Node> nameWiseNodeMapping = new HashMap<>();

        for(Node node : graph.getNodes()) {
            nameWiseNodeMapping.put(node.getName(), node);
        }

        return nameWiseNodeMapping;

    }

    public static String getRootNode(Graph graph) {

        Map<String, List<String>> nodeToParentNodeRelation = getNodeToParentNodeRelation(graph);

        for(Map.Entry<String, List<String>> relation : nodeToParentNodeRelation.entrySet()) {

            if(relation.getValue() == null) {
                return relation.getKey();
            }
        }

        return null;
    }

    public static Map<String, NodeConfiguration> getNodeNameToConfigurationMapping(GraphConfiguration graphConfiguration) {

        Map<String, NodeConfiguration> nodeNameToConfigurationMapping = new HashMap<>();

        for(TeamConfiguration teamConfiguration : graphConfiguration.getTeamConfigurations()) {

            for(NodeConfiguration nodeConfiguration : teamConfiguration.getNodeConfigurations()) {

                Map<String, String>  nodeHeaders = nodeConfiguration.getHeaders();

                if(nodeHeaders == null) {
                    nodeHeaders = new HashMap<>();
                    nodeConfiguration.setHeaders(nodeHeaders);
                }

                nodeHeaders.putAll(teamConfiguration.getHeaders());
                nodeNameToConfigurationMapping.put(nodeConfiguration.getName(), nodeConfiguration);
            }
        }

        return nodeNameToConfigurationMapping;
    }

    public static Map<String, TeamConfiguration> getTeamNameToConfigurationMapping(GraphConfiguration graphConfiguration) {

        Map<String, TeamConfiguration> teamNameToConfigurationMapping = new HashMap<>();

        for(TeamConfiguration teamConfiguration : graphConfiguration.getTeamConfigurations()) {
            teamNameToConfigurationMapping.put(teamConfiguration.getOwner(), teamConfiguration);
        }

        return teamNameToConfigurationMapping;
    }
}
