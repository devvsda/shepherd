package com.devsda.platform.shephardcore.service;

import com.devsda.platform.shephardcore.dao.RegisterationDao;
import com.devsda.platform.shephardcore.dao.WorkflowOperationDao;
import com.devsda.platform.shephardcore.model.NodeResponse;
import com.devsda.platform.shephardcore.util.GraphUtil;
import com.devsda.platform.shepherd.constants.GraphType;
import com.devsda.platform.shepherd.constants.NodeState;
import com.devsda.platform.shepherd.constants.WorkflowExecutionState;
import com.devsda.platform.shepherd.exception.ClientNodeFailureException;
import com.devsda.platform.shepherd.exception.NodeFailureException;
import com.devsda.platform.shepherd.model.*;
import com.devsda.platform.shepherd.util.DateUtil;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class ExecuteWorkflowRunner implements Callable<Void> {

    private static final Logger log = LoggerFactory.getLogger(ExecuteWorkflowRunner.class);

    @Inject
    private static RegisterationDao registerationDao;

    @Inject
    private static WorkflowOperationDao workflowOperationDao;

    @Inject
    private static ExecuteWorkflowServiceHelper executeWorkflowServiceHelper;

    private ExecuteWorkflowRequest executeWorkflowRequest;
    private Graph graph;
    private GraphConfiguration graphConfiguration;

    public ExecuteWorkflowRunner(Graph graph, GraphConfiguration graphConfiguration, ExecuteWorkflowRequest executeWorkflowRequest) {
        this.graph = graph;
        this.graphConfiguration = graphConfiguration;
        this.executeWorkflowRequest = executeWorkflowRequest;
    }

    /**
     * This method executes graph
     *
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Override
    public Void call() throws InterruptedException, ExecutionException {

        // TODO : Need to push all the rows to Redis.
        Map<String, Node> nodeNameToNodeMapping = GraphUtil.getNodeNameToNodePOJOMapping(this.executeWorkflowRequest.getObjectId(), this.executeWorkflowRequest.getExecutionId(), this.graph, this.graphConfiguration);

        Node rootNode = GraphUtil.getRootNode(nodeNameToNodeMapping);

        // TODO : Remove this.
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        log.info(String.format("Created thread-pool of count : %s", 10));

        log.info(String.format("Submitting node : %s to thread-pool for execution", rootNode));


        Future<NodeResponse> rootNodeFuture = executorService.submit(new NodeExecutor(rootNode));

        // TODO : Remove this. No need of this logic, once RabbitMQ comes into picture.
        Deque<Future<NodeResponse>> futureObjects = new LinkedList<>();
        futureObjects.addFirst(rootNodeFuture);

        while (!futureObjects.isEmpty()) {

            Future<NodeResponse> thisFutureObject = futureObjects.removeFirst();

            try {
                NodeResponse nodeResponse = thisFutureObject.get(10000l, TimeUnit.MILLISECONDS);

                String nodeName = nodeResponse.getNodeName();
                NodeState nodeState = nodeResponse.getNodeState();

                if (!NodeState.COMPLETED.equals(nodeState)) {
                    continue;
                }

                log.info(String.format("Node : %s successfully completed", nodeName));


                List<Connection> childrenConnections = nodeNameToNodeMapping.get(nodeName).getConnections();

                if (childrenConnections == null) {
                    continue;
                }

                log.debug(String.format("Number of children of node : %s is %s", nodeName, childrenConnections.size()));

                for (Connection connection : childrenConnections) {

                    String childNodeName = connection.getNodeName();

                    if(GraphType.CONDITIONAL.equals(graph.getGraphType())) {

                        if (connection.getEdgeName().equalsIgnoreCase(nodeResponse.getClientResponse())) {

                            pushNodeToQueue(childNodeName, nodeNameToNodeMapping,
                                    executorService, futureObjects);

                        } else {
                            continue;
                        }

                    } else {

                        Boolean isNodeReadyToExecute = executeWorkflowServiceHelper.isNodeReadyToExecute(connection.getNode());

                        if (Boolean.TRUE.equals(isNodeReadyToExecute)) {

                            pushNodeToQueue(childNodeName, nodeNameToNodeMapping,
                                    executorService, futureObjects);

                        } else {
                            // TODO : Need to maintain secondary Queue to avoid this node becoming Zombie.
                        }
                    }
                }

            } catch (TimeoutException e) {
                log.info(String.format("Node failed because of timeOut. Pushing it again."));
                futureObjects.addLast(thisFutureObject);
            } catch (ClientNodeFailureException | NodeFailureException e) {

                log.error(String.format("Execution : %s failed.", executeWorkflowRequest.getExecutionId()), e);
                executeWorkflowRequest.setWorkflowExecutionState(WorkflowExecutionState.FAILED);
                executeWorkflowRequest.setUpdatedAt(DateUtil.currentDate());
                executeWorkflowRequest.setErrorMessage(e.getLocalizedMessage());
                workflowOperationDao.updateExecutionStatus(executeWorkflowRequest.getObjectId(), executeWorkflowRequest.getExecutionId(),
                        executeWorkflowRequest.getWorkflowExecutionState(), executeWorkflowRequest.getErrorMessage());

            } catch (Exception e) {

                log.error(String.format("Execution : %s failed.", executeWorkflowRequest.getExecutionId()), e);
                executeWorkflowRequest.setWorkflowExecutionState(WorkflowExecutionState.FAILED);
                executeWorkflowRequest.setUpdatedAt(DateUtil.currentDate());
                executeWorkflowRequest.setErrorMessage(e.getLocalizedMessage());
                workflowOperationDao.updateExecutionStatus(executeWorkflowRequest.getObjectId(), executeWorkflowRequest.getExecutionId(),
                        executeWorkflowRequest.getWorkflowExecutionState(), executeWorkflowRequest.getErrorMessage());

            }
        }

        executorService.shutdown();

        return null;
    }

    private void pushNodeToQueue(String childNodeName, Map<String, Node> nodeNameToNodeMapping,
                                 ExecutorService executorService, Deque<Future<NodeResponse>> futureObjects) {
        log.info(String.format("Pushing node : %s to Queue", childNodeName));

        Node thisNodeObj = nodeNameToNodeMapping.get(childNodeName);
        thisNodeObj.setObjectId(this.executeWorkflowRequest.getObjectId());
        thisNodeObj.setExecutionId(this.executeWorkflowRequest.getExecutionId());
        Future<NodeResponse> childNodeResponse = executorService.submit(new NodeExecutor(thisNodeObj));
        futureObjects.addLast(childNodeResponse);
    }
}
