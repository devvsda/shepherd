package com.devsda.platform.shepherdcore.service;

import com.devsda.platform.shepherdcore.dao.WorkflowOperationDao;
import com.devsda.platform.shepherdcore.loader.JSONLoader;
import com.devsda.platform.shepherd.model.ExecutionDetails;
import com.devsda.platform.shepherdcore.model.NodeResponse;
import com.devsda.platform.shepherdcore.service.documentservice.ExecutionDocumentService;
import com.devsda.platform.shepherd.constants.NodeState;
import com.devsda.platform.shepherd.constants.ShepherdConstants;
import com.devsda.platform.shepherd.constants.WorkflowExecutionState;
import com.devsda.platform.shepherd.exception.ClientNodeFailureException;
import com.devsda.platform.shepherd.exception.NodeFailureException;
import com.devsda.platform.shepherd.model.*;
import com.devsda.platform.shepherd.util.DateUtil;
import com.devsda.utils.httputils.methods.HttpPostMethod;
import com.google.inject.Inject;
import org.apache.http.client.HttpResponseException;
import org.apache.http.entity.StringEntity;
import org.bson.Document;
import org.skife.jdbi.v2.exceptions.UnableToExecuteStatementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

public class NodeExecutor implements Callable<NodeResponse> {

    private static final Logger log = LoggerFactory.getLogger(NodeExecutor.class);

    @Inject
    private static WorkflowOperationDao workflowOperationDao;

    @Inject
    private static ExecutionDocumentService executionDocumentService;

    @Inject
    private static ExecuteWorkflowServiceHelper executeWorkflowServiceHelper;

    private Node node;

    public NodeExecutor(Node node) {
        this.node = node;
    }

    @Override
    public NodeResponse call() throws Exception {

        String response = null;
        Node node = null;
        NodeConfiguration nodeConfiguration = node.getNodeConfiguration();
        ServerDetails serverDetails = nodeConfiguration.getServerDetails();

        log.info(String.format("Executing node : %s. NodeConfiguration : %s", nodeConfiguration.getName(), nodeConfiguration));

        try {

            Boolean isExecutionInKilledState = isExecutionInKilledState();

            if (isExecutionInKilledState) {
                NodeResponse nodeResponse = killThisNode();
                return nodeResponse;
            }

            // Create node with PROCESSING state.
            createNodeInDB();

            ShepherdExecutionResponse clientNodeResponse = null;

            // Fetch document of this execution.
            Document executionDetailsDoc = executionDocumentService.fetchExecutionDetails(this.node.getObjectId(),this.node.getExecutionId());
            String executionDataJson= ((Document)executionDetailsDoc.get("executionData")).toJson();

            // Execute Node.
            HttpPostMethod clientNodeRequest= new HttpPostMethod();
            clientNodeResponse = clientNodeRequest.call(serverDetails.getProtocol(), serverDetails.getHostName(), serverDetails.getPort(),
                    nodeConfiguration.getURI(), null , nodeConfiguration.getHeaders(),
                    new StringEntity(executionDataJson), ShepherdExecutionResponse.class);

            boolean isExecutionDocumentUpdated = executionDocumentService.updateExecutionDetails(this.node.getObjectId(),this.node.getExecutionId(), clientNodeResponse.getExecutionData());

            log.info(String.format("Response of Node : %s is %s, isExecutionDocumentUpdated %s", nodeConfiguration.getName(), clientNodeResponse.getResponseEdge(),isExecutionDocumentUpdated));

            // Update Node status as Completed in Node table.
            updateNodeStatus(NodeState.COMPLETED);

            // TODO: Push Children nodes.
            // pushChildrenNodesToRabbitMQ(clientNodeResponse.getResponseEdge());

            return new NodeResponse(nodeConfiguration.getName(), NodeState.COMPLETED, response);

        } catch(UnableToExecuteStatementException e) {
            // This exception occurs in UNCONDITIONAL workflow. When we try to execute same node twice.
            log.info(String.format("Node : %s already under processing. Skipping this execution to avoid duplicity.",
                    nodeConfiguration.getName()), e);
            return new NodeResponse(nodeConfiguration.getName(), NodeState.SKIPPED, null);

        } catch (HttpResponseException e) {

            log.error(String.format("Node : %s failed at client side.", nodeConfiguration.getName()), e);
            updateNodeStatus(NodeState.FAILED);
            throw new ClientNodeFailureException(String.format("Node : %s failed at client side.", nodeConfiguration.getName()), e);

        } catch (Exception e) {

            log.error(String.format("Node : %s failed internally.", nodeConfiguration.getName()), e);
            updateNodeStatus(NodeState.FAILED);
            throw new NodeFailureException(String.format("Node : %s failed internally.", nodeConfiguration.getName()), e);

        }
    }

    private void pushChildrenNodesToRabbitMQ(String edgeName) {

        Boolean isGraphConditional = (this.node.getParentNodes() == null);

        if(isGraphConditional) {

            for(Connection connection : this.node.getConnections()) {

                if(connection.getEdgeName().equalsIgnoreCase(edgeName)) {
                    // TODO : Push node-message to primary queue : RabbitMQ.
                    return;
                }
            }
        } else {

            for (Connection connection : this.node.getConnections()) {

                Boolean isChildNodeReadyToExecute = executeWorkflowServiceHelper.isNodeReadyToExecute(connection.getNode());

                if(isChildNodeReadyToExecute) {
                    // TODO : Push to primary Queue
                } else {
                    // TODO : Push to secondary Queue.
                }
            }
        }
    }

    private Boolean isExecutionInKilledState() {

        // Check weather execution is killed by customer or not. If yes, then no need to execute this node.
        ExecutionDetails executionDetails = workflowOperationDao.getExecutionDetails(this.node.getObjectId(), this.node.getExecutionId());

        if ( WorkflowExecutionState.KILLED.equals(executionDetails.getWorkflowExecutionState()) ) {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    private NodeResponse killThisNode() {
        log.info(String.format("Execution id : %s is in killed state. Skipping execution of ndoe : %s",
                this.node.getExecutionId(), this.node.getName()));
        this.node.setUpdatedAt(DateUtil.currentDate());
        this.node.setNodeState(NodeState.KILLED);
        this.node.setSubmittedBy(ShepherdConstants.PROCESS_OWNER);
        workflowOperationDao.createNode(this.node);
        return new NodeResponse(this.node.getName(), NodeState.KILLED, null);
    }

    private void createNodeInDB() {
        this.node.setUpdatedAt(DateUtil.currentDate());
        this.node.setNodeState(NodeState.PROCESSING);
        this.node.setSubmittedBy(ShepherdConstants.PROCESS_OWNER);
        workflowOperationDao.createNode(this.node);
    }

    private void updateNodeStatus(NodeState nodeState) {
        this.node.setNodeState(nodeState);
        this.node.setUpdatedAt(DateUtil.currentDate());
        workflowOperationDao.updateNode(this.node);
    }
}
