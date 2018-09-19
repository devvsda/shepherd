package com.devsda.platform.shephardcore.service;

import com.devsda.platform.shepherd.constants.NodeState;
import com.devsda.platform.shepherd.graphgenerator.DAGGenerator;
import com.devsda.platform.shephardcore.loader.JSONLoader;
import com.devsda.platform.shephardcore.model.*;
import com.devsda.platform.shephardcore.util.GraphUtil;
import com.devsda.platform.shepherd.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class ExecuteWorkflowService {

    private static final Logger log = LoggerFactory.getLogger(ExecuteWorkflowService.class);

    /**
     * This method helps to execute workflow.
     * @param clientId Execute workflow for given client.
     * @param endpointId Execute endpoint of above client.
     * @param initialPayload Initial payload, which will be consumed by root node of workflow.
     * @throws Exception
     */
    public void executeWorkflow(String clientId, String endpointId, Map<String, Object> initialPayload) throws Exception {


        // TODO : Fetch required details from Database layer.
        // TODO : For now, I am using sample workflow , and its configurations.

        // Generate graph details.
        DAGGenerator dagGenerator = new DAGGenerator();
        String workflowFilePath = "./src/test/resources/sample_workflow.xml";
        Graph graph = dagGenerator.generate(workflowFilePath);

        // Load graph configurations.
        String workflowConfigurationPath = "./src/test/resources/workflow_configuration.json";
        GraphConfiguration graphConfiguration = JSONLoader.load(workflowConfigurationPath, GraphConfiguration.class);

        log.info(String.format("Graph : %s. GraphConfiguration : %s", graph, graphConfiguration));

        // Graph
        executeGraph(graph, graphConfiguration);

    }


    /**
     * This method executes graph
     * @param graph directed acyclic graph
     * @param graphConfiguration configuration of all nodes under given graph
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private void executeGraph(Graph graph, GraphConfiguration graphConfiguration) throws InterruptedException, ExecutionException {

        Map<String, NodeConfiguration> nodeNameToNodeConfigurationMapping = GraphUtil.getNodeNameToNodeConfigurationMapping(graphConfiguration);
        Map<String, TeamConfiguration> teamNameToTeamConfigurationMapping = GraphUtil.getTeamNameToTeamConfigurationMapping(graphConfiguration);

        Map<String, Node> nodeNameToNodeMapping = GraphUtil.getNodeNameToNodeMapping(graph);

        Map<String, List<String>> nodeToParentNodesMapping = GraphUtil.getNodeToParentNodesMapping(graph);

        String rootNode = GraphUtil.getRootNode(graph);

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        log.info(String.format("Created thread-pool of count : %s", 10));

        NodeConfiguration rootNodeConfiguration = nodeNameToNodeConfigurationMapping.get(rootNode);
        ServerDetails rootNodeServerDetails = teamNameToTeamConfigurationMapping.get(nodeNameToNodeMapping.get(rootNode).getOwner()).getServerDetails();

        log.info(String.format("Submitting node : %s to thread-pool for execution", rootNode));
        Future<NodeResponse> rootNodeFuture = executorService.submit(new NodeExecutor(rootNodeConfiguration, rootNodeServerDetails));

        Deque<Future<NodeResponse>> futureObjects = new LinkedList<>();
        futureObjects.addFirst(rootNodeFuture);

        while(!futureObjects.isEmpty()) {

            Future<NodeResponse> thisFutureObject = futureObjects.removeFirst();

            try {
                NodeResponse nodeResponse = thisFutureObject.get(1000l, TimeUnit.MILLISECONDS);

                String nodeName = nodeResponse.getNodeName();
                log.info(String.format("Node : %s successfully completed", nodeName));

                nodeNameToNodeMapping.get(nodeName).setNodeState(NodeState.COMPLETED);

                // TODO : This will use in CONDITIONAL workflow execution.
                String clientResponse = nodeResponse.getClientResponse();

                List<Connection> childrenConnections = nodeNameToNodeMapping.get(nodeName).getConnections();

                if(childrenConnections == null) {
                    continue;
                }

                log.debug(String.format("Number of children of node : %s is %s", nodeName, childrenConnections.size()));

                for(Connection connection : childrenConnections) {

                    // TODO : This will use in CONDITIONAL workflow execution.
                    String edgeName = connection.getEdgeName();

                    String childNodeName = connection.getNodeName();

                    Boolean isNodeReadyToExecute = ExecuteWorkflowServiceHelper.isNodeReadyToExecute(childNodeName, nodeToParentNodesMapping, nodeNameToNodeMapping);

                    if (Boolean.TRUE.equals(isNodeReadyToExecute)) {
                        NodeConfiguration childNodeConfiguration = nodeNameToNodeConfigurationMapping.get(childNodeName);
                        ServerDetails childNodeServerDetails = teamNameToTeamConfigurationMapping.get(nodeNameToNodeMapping.get(childNodeName).getOwner()).getServerDetails();

                        Future<NodeResponse> childNodeResponse = executorService.submit(new NodeExecutor(childNodeConfiguration, childNodeServerDetails));
                        futureObjects.addLast(childNodeResponse);
                    } else {
                        // Nothing to do.
                    }

                }

            } catch(TimeoutException e) {
                log.info(String.format("Node failed because of timeOut. Pushing it again."));
                futureObjects.addLast(thisFutureObject);
            }
        }

        return;
    }
}
