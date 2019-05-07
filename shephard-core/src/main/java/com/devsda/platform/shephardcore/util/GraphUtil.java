package com.devsda.platform.shephardcore.util;

import com.devsda.platform.shepherd.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphUtil {

    private static final Logger log = LoggerFactory.getLogger(GraphUtil.class);

    public static Map<String, List<String>> getNodeToParentNodesMapping(Graph graph) {

        Map<String, List<String>> nodeToParentNodesRelation = new HashMap<>();

        for (Node node : graph.getNodes()) {
            nodeToParentNodesRelation.put(node.getName(), null);
        }

        for (Node node : graph.getNodes()) {
            if (node.getConnections() == null) continue;

            for (Connection connection : node.getConnections()) {
                String destinationNode = connection.getNodeName();
                List<String> parentNodes = nodeToParentNodesRelation.get(destinationNode);
                ;

                if (parentNodes == null) {
                    parentNodes = new ArrayList<>();

                }

                parentNodes.add(node.getName());
                nodeToParentNodesRelation.put(destinationNode, parentNodes);
            }
        }

        return nodeToParentNodesRelation;
    }

    public static Map<String, Node> getNodeNameToNodeMapping(Graph graph) {

        log.debug(String.format("Generating node name to node details mapping."));

        Map<String, Node> nameWiseNodeMapping = new HashMap<>();

        for (Node node : graph.getNodes()) {
            nameWiseNodeMapping.put(node.getName(), node);
        }

        return nameWiseNodeMapping;

    }

    public static String getRootNode(Graph graph) {

        Map<String, List<String>> nodeToParentNodeRelation = getNodeToParentNodesMapping(graph);

        for (Map.Entry<String, List<String>> relation : nodeToParentNodeRelation.entrySet()) {

            if (relation.getValue() == null) {
                return relation.getKey();
            }
        }

        return null;
    }

    public static Map<String, NodeConfiguration> getNodeNameToNodeConfigurationMapping(GraphConfiguration graphConfiguration) {

        log.debug(String.format("Generating node name to node configuration mapping."));

        Map<String, NodeConfiguration> nodeNameToConfigurationMapping = new HashMap<>();

        for (TeamConfiguration teamConfiguration : graphConfiguration.getTeamConfigurations()) {

            for (NodeConfiguration nodeConfiguration : teamConfiguration.getNodeConfigurations()) {

                Map<String, String> nodeHeaders = nodeConfiguration.getHeaders();

                if (nodeHeaders == null) {
                    nodeHeaders = new HashMap<>();
                    nodeConfiguration.setHeaders(nodeHeaders);
                }

                nodeHeaders.putAll(teamConfiguration.getHeaders());
                nodeNameToConfigurationMapping.put(nodeConfiguration.getName(), nodeConfiguration);
            }
        }

        return nodeNameToConfigurationMapping;
    }

    public static Map<String, TeamConfiguration> getTeamNameToTeamConfigurationMapping(GraphConfiguration graphConfiguration) {

        log.debug(String.format("Generating team name to team configuration mapping."));

        Map<String, TeamConfiguration> teamNameToConfigurationMapping = new HashMap<>();

        for (TeamConfiguration teamConfiguration : graphConfiguration.getTeamConfigurations()) {
            teamNameToConfigurationMapping.put(teamConfiguration.getOwner(), teamConfiguration);
        }

        return teamNameToConfigurationMapping;
    }
}
