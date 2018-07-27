package com.devsda.platform.shephardcore.service;

import com.devsda.platform.shephardcore.constants.NodeState;
import com.devsda.platform.shephardcore.graphgenerator.DAGGenerator;
import com.devsda.platform.shephardcore.loader.JSONLoader;
import com.devsda.platform.shephardcore.model.*;
import com.devsda.platform.shephardcore.util.GraphUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class ExecuteWorkflowService {

    private static final Logger log = LoggerFactory.getLogger(ExecuteWorkflowService.class);

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

        log.info(String.format("Graph : %s", graph));
        log.info(String.format("GraphConfiguration : %s", graphConfiguration));

        // Graph
        executeGraph(graph, graphConfiguration);

    }

    private void executeGraph(Graph graph, GraphConfiguration graphConfiguration) throws InterruptedException, ExecutionException {

        Map<String, NodeConfiguration> nodeNameToConfigurationMapping = GraphUtil.getNodeNameToConfigurationMapping(graphConfiguration);
        Map<String, TeamConfiguration> teamNameToConfigurationMapping = GraphUtil.getTeamNameToConfigurationMapping(graphConfiguration);

        Map<String, Node> nameWiseNodeMapping = GraphUtil.nameWiseMapping(graph);

        Map<String, List<String>> nodeToParentNodeMapping = GraphUtil.getNodeToParentNodeRelation(graph);

        String rootNode = GraphUtil.getRootNode(graph);

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        log.info(String.format("Created threadpool of count : %s", 10));

        NodeConfiguration rootNodeConfiguration = nodeNameToConfigurationMapping.get(rootNode);
        ServerDetails rootNodeServerDetails = teamNameToConfigurationMapping.get(nameWiseNodeMapping.get(rootNode).getOwner()).getServerDetails();

        log.info(String.format("Submitting node : %s to threadpool for execution", rootNode));
        Future<NodeResponse> rootNodeFuture = executorService.submit(new NodeExecutor(rootNodeConfiguration, rootNodeServerDetails));

        Deque<Future<NodeResponse>> futureObjects = new LinkedList<>();
        futureObjects.addFirst(rootNodeFuture);

        while(!futureObjects.isEmpty()) {

            Future<NodeResponse> thisFutureObject = futureObjects.removeFirst();


            try {
                NodeResponse nodeResponse = thisFutureObject.get(1000, TimeUnit.MILLISECONDS);

                String nodeName = nodeResponse.getNodeName();
                log.info(String.format("Node : %s successfully completed", nodeName));

                nameWiseNodeMapping.get(nodeName).setNodeState(NodeState.COMPLETED);

                // TODO : This will use in CONDITIONAL workflow execution.
                String clientResponse = nodeResponse.getClientResponse();

                List<Connection> childrenConnections = nameWiseNodeMapping.get(nodeName).getConnections();

                if(childrenConnections == null) {
                    continue;
                }

                log.info(String.format("Number of children of node : %s is %s", nodeName, childrenConnections.size()));

                for(Connection connection : childrenConnections) {

                    // TODO : This will use in CONDITIONAL workflow execution.
                    String edgeName = connection.getEdgeName();

                    String childNodeName = connection.getNodeName();

                    Boolean isNodeReadyToExecute = ExecuteWorkflowServiceHelper.isNodeReadyToExecute(childNodeName, nodeToParentNodeMapping, nameWiseNodeMapping);

                    if (Boolean.TRUE.equals(isNodeReadyToExecute)) {
                        NodeConfiguration childNodeConfiguration = nodeNameToConfigurationMapping.get(childNodeName);
                        ServerDetails childNodeServerDetails = teamNameToConfigurationMapping.get(nameWiseNodeMapping.get(childNodeName).getOwner()).getServerDetails();

                        Future<NodeResponse> childNodeResponse = executorService.submit(new NodeExecutor(childNodeConfiguration, childNodeServerDetails));
                        futureObjects.addLast(childNodeResponse);
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
