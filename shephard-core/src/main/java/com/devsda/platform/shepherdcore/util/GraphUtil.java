package com.devsda.platform.shepherdcore.util;

import com.devsda.platform.shepherd.constants.GraphType;
import com.devsda.platform.shepherd.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphUtil {

    private static final Logger log = LoggerFactory.getLogger(GraphUtil.class);

    public static Node getRootNode(Map<String, Node> nodeNameToNodePOJOMapping) {

        for (Map.Entry<String, Node> nodeEntry : nodeNameToNodePOJOMapping.entrySet()) {

            Node node = nodeEntry.getValue();

            if(node.getParentNodes() == null) {
                return node;
            }
        }

        return null;
    }

    public static Map<String, List<String>> getNodeNameToParentNodePOJOsMapping(Graph graph) {

        Map<String, List<String>> nodeToParentNodesRelation = new HashMap<>();

        for (Node node : graph.getNodes()) {
            nodeToParentNodesRelation.put(node.getName(), null);
        }

        for (Node node : graph.getNodes()) {
            if (node.getConnections() == null) continue;

            for (Connection connection : node.getConnections()) {
                String destinationNode = connection.getNodeName();
                List<String> parentNodes = nodeToParentNodesRelation.get(destinationNode);

                if (parentNodes == null) {
                    parentNodes = new ArrayList<>();

                }

                parentNodes.add(node.getName());
                nodeToParentNodesRelation.put(destinationNode, parentNodes);
            }
        }

        return nodeToParentNodesRelation;
    }

    public static Map<String, Node> getNodeNameToNodePOJOMapping(String objectId, String executionId, Graph graph, GraphConfiguration graphConfiguration) {

        log.debug(String.format("Generating node name to node configuration mapping."));

        Map<String, Node> nameWiseNodeMapping = new HashMap<>();

        for (Node node : graph.getNodes()) {

            node.setObjectId(objectId);
            node.setExecutionId(executionId);

            nameWiseNodeMapping.put(node.getName(), node);
        }

        setGlobalSettingsInNode(nameWiseNodeMapping, graphConfiguration);
        if (GraphType.UNCONDITIONAL.equals(graph.getGraphType())) {
            setParentNodes(nameWiseNodeMapping, graph);
        }
        setChildrenNodes(nameWiseNodeMapping);

        return nameWiseNodeMapping;
    }

    public static void setParentNodes(Map<String, Node> nameWiseNodeMapping, Graph graph ) {

        Map<String, List<String>> nodeNameToParentNodePOJOsMapping = getNodeNameToParentNodePOJOsMapping(graph);

        for (Map.Entry<String, Node> nodeEntry : nameWiseNodeMapping.entrySet() ) {

            Node node = nodeEntry.getValue();

            List<String> parentNodeNames = nodeNameToParentNodePOJOsMapping.get(node.getName());

            node.setParentNodes(parentNodeNames);
        }

    }

    public static void setChildrenNodes(Map<String, Node> nameWiseNodeMapping) {

        for(Map.Entry<String, Node> nodeEntry : nameWiseNodeMapping.entrySet()) {

            Node node = nodeEntry.getValue();

            for(Connection connection : node.getConnections()) {

                connection.setNode(nameWiseNodeMapping.get(connection.getNodeName()));
            }
        }
    }

    public static void setGlobalSettingsInNode(Map<String, Node> nameWiseNodeMapping, GraphConfiguration graphConfiguration) {

        for (TeamConfiguration teamConfiguration : graphConfiguration.getTeamConfigurations()) {

            for (NodeConfiguration nodeConfiguration : teamConfiguration.getNodeConfigurations()) {

                // Update headers.
                Map<String, String> nodeHeaders = new HashMap<>(nodeConfiguration.getHeaders());

                nodeConfiguration.setHeaders(teamConfiguration.getHeaders());

                if (nodeHeaders != null) {
                    nodeConfiguration.getHeaders().putAll(nodeHeaders);
                }

                // Add server details.
                nodeConfiguration.setServerDetails(teamConfiguration.getServerDetails());

                Node node = nameWiseNodeMapping.get(nodeConfiguration.getName());
                node.setNodeConfiguration(nodeConfiguration);
            }
        }
    }
}
