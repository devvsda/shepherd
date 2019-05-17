package com.devsda.platform.shepherdcore.service;

<<<<<<< HEAD:shephard-core/src/main/java/com/devsda/platform/shepherdcore/service/NodeExecutor.java
import com.devsda.platform.shepherdcore.dao.WorkflowOperationDao;
import com.devsda.platform.shepherdcore.loader.JSONLoader;
import com.devsda.platform.shepherd.model.ExecutionDetails;
import com.devsda.platform.shepherdcore.model.NodeResponse;
import com.devsda.platform.shepherdcore.service.documentservice.ExecutionDocumentService;
=======
import com.devsda.platform.shephardcore.dao.WorkflowOperationDao;
import com.devsda.platform.shephardcore.loader.JSONLoader;
import com.devsda.platform.shepherd.model.ExecutionDetails;
import com.devsda.platform.shephardcore.model.NodeResponse;
import com.devsda.platform.shephardcore.service.documentservice.ExecutionDocumentService;
>>>>>>> a2832127c981e8796ce10e91b5055513159a5aa6:shephard-core/src/main/java/com/devsda/platform/shephardcore/service/NodeExecutor.java
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

    private NodeConfiguration nodeConfiguration;
    private ServerDetails serverDetails;
    private Node node;

    public NodeExecutor(Node node, NodeConfiguration nodeConfiguration, ServerDetails serverDetails) {
        this.node = node;
        this.nodeConfiguration = nodeConfiguration;
        this.serverDetails = serverDetails;
    }

    @Override
    public NodeResponse call() throws Exception {


        log.info(String.format("Executing node : %s. NodeConfiguration : %s, Server details : %s", nodeConfiguration.getName(), nodeConfiguration, serverDetails));

        String response = null;
        Node node = null;

        try {

            ExecutionDetails executionDetails = workflowOperationDao.getExecutionDetails(this.node.getObjectId(), this.node.getExecutionId());

            if (WorkflowExecutionState.KILLED.equals(executionDetails.getWorkflowExecutionState())) {
                log.info(String.format("Execution id : %s is in killed state. Skipping execution of ndoe : %s",
                        this.node.getExecutionId(), this.node.getName()));
                this.node.setUpdatedAt(DateUtil.currentDate());
                this.node.setNodeState(NodeState.KILLED);
                this.node.setSubmittedBy(ShepherdConstants.PROCESS_OWNER);
                workflowOperationDao.createNode(this.node);
                return new NodeResponse(nodeConfiguration.getName(), NodeState.KILLED, response);
            }

            // 1. Create entry in Node table.
            this.node.setUpdatedAt(DateUtil.currentDate());
            this.node.setNodeState(NodeState.PROCESSING);
            this.node.setSubmittedBy(ShepherdConstants.PROCESS_OWNER);
            workflowOperationDao.createNode(this.node);
            ShepherdExecutionResponse clientNodeResponse = null;

            Document executionDetailsDoc = executionDocumentService.fetchExecutionDetails(this.node.getObjectId(),this.node.getExecutionId());
            try {
                String executionDataJson= ((Document)executionDetailsDoc.get("executionData")).toJson();
<<<<<<< HEAD:shephard-core/src/main/java/com/devsda/platform/shepherdcore/service/NodeExecutor.java
                ExecutionData executionData = JSONLoader.loadFromStringifiedObject(executionDataJson, ExecutionData.class);
=======
>>>>>>> a2832127c981e8796ce10e91b5055513159a5aa6:shephard-core/src/main/java/com/devsda/platform/shephardcore/service/NodeExecutor.java

                // 2. Execute Node.
                HttpPostMethod clientNodeRequest= new HttpPostMethod();
                clientNodeResponse = clientNodeRequest.call(serverDetails.getProtocol(), serverDetails.getHostName(), serverDetails.getPort(),
                        nodeConfiguration.getURI(), null , nodeConfiguration.getHeaders(),
<<<<<<< HEAD:shephard-core/src/main/java/com/devsda/platform/shepherdcore/service/NodeExecutor.java
                        new StringEntity(JSONLoader.stringify(executionData)), ShepherdExecutionResponse.class);
=======
                        new StringEntity(executionDataJson), ShepherdExecutionResponse.class);
>>>>>>> a2832127c981e8796ce10e91b5055513159a5aa6:shephard-core/src/main/java/com/devsda/platform/shephardcore/service/NodeExecutor.java

            }catch(Exception ex){
                throw ex;
            }

            boolean isExecutionDocumentUpddated = executionDocumentService.updateExecutionDetails(this.node.getObjectId(),this.node.getExecutionId(), clientNodeResponse.getExecutionData());

            log.info(String.format("Response of Node : %s is %s, isExecutionDocumentUpddated %s", nodeConfiguration.getName(), clientNodeResponse.getResponseEdge(),isExecutionDocumentUpddated));

            // 3. Update Node status as Completed in Node table.
            this.node.setNodeState(NodeState.COMPLETED);
            this.node.setUpdatedAt(DateUtil.currentDate());
            workflowOperationDao.updateNode(this.node);

            return new NodeResponse(nodeConfiguration.getName(), NodeState.COMPLETED, response);

        } catch(UnableToExecuteStatementException e) {
            log.info(String.format("Node : %s already under processing. Skipping this execution to avoid duplicity.",
                    this.nodeConfiguration.getName()), e);
            return new NodeResponse(nodeConfiguration.getName(), NodeState.SKIPPED, null);

        } catch (HttpResponseException e) {

            log.error(String.format("Node : %s failed at client side.", this.nodeConfiguration.getName()), e);
            this.node.setNodeState(NodeState.FAILED);
            this.node.setUpdatedAt(DateUtil.currentDate());
            workflowOperationDao.updateNode(this.node);
            throw new ClientNodeFailureException(String.format("Node : %s failed at client side.", this.nodeConfiguration.getName()), e);

        } catch (Exception e) {

            log.error(String.format("Node : %s failed internally.", this.nodeConfiguration.getName()), e);
            this.node.setNodeState(NodeState.FAILED);
            this.node.setUpdatedAt(DateUtil.currentDate());
            workflowOperationDao.updateNode(this.node);
            throw new NodeFailureException(String.format("Node : %s failed internally.", this.nodeConfiguration.getName()), e);

        }
    }
}
