package com.devsda.platform.shepherdcore.service;

import com.devsda.platform.shepherd.constants.WorkflowExecutionState;
import com.devsda.platform.shepherd.model.*;
import com.devsda.platform.shepherd.util.DateUtil;
import com.devsda.platform.shepherdcore.constants.ShephardConstants;
import com.devsda.platform.shepherdcore.dao.WorkflowOperationDao;
import com.devsda.platform.shepherd.constants.NodeState;
import com.devsda.platform.shepherdcore.loader.JSONLoader;
import com.devsda.platform.shepherdcore.service.queueservice.RabbitMQOperation;
import com.devsda.platform.shepherdcore.util.GraphUtil;
import com.google.inject.Inject;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class ExecuteWorkflowServiceHelper {

    private static final Logger log = LoggerFactory.getLogger(ExecuteWorkflowServiceHelper.class);

    @Named(ShephardConstants.RabbitMQ.PUBLISHER)
    @Inject
    private Connection publisherConnection;

    @Inject
    private WorkflowOperationDao workflowOperationDao;

    @Inject
    private RabbitMQOperation rabbitMQOperation;

    public Boolean isNodeReadyToExecute(Node node) {

        log.info(String.format("Checking weather node : %s is ready to execute", node.getName()));

        List<String> parentNodeNames = node.getParentNodes();

        for (String parentNodeName : parentNodeNames) {

            Node parentNodeDao = workflowOperationDao.getNode(parentNodeName, node.getObjectId(), node.getExecutionId());

            log.info(String.format("Node state as per DAO : %s", parentNodeDao));

            if (parentNodeDao == null || !NodeState.COMPLETED.equals(parentNodeDao.getNodeState())) {
                log.info(String.format("Node : %s is not ready to execute.", node.getName()));
                return Boolean.FALSE;
            }
        }

        log.info(String.format("Node : %s is ready to execute.", node.getName()));
        return Boolean.TRUE;
    }

    public void triggerExecution(ExecuteWorkflowRequest executeWorkflowRequest, Graph graph, GraphConfiguration graphConfiguration) throws InterruptedException, ExecutionException {

        // TODO : Need to push all the rows to Redis.
        Map<String, Node> nodeNameToNodeMapping = GraphUtil.getNodeNameToNodePOJOMapping(executeWorkflowRequest.getObjectId(), executeWorkflowRequest.getExecutionId(), graph, graphConfiguration);

        Node rootNode = GraphUtil.getRootNode(nodeNameToNodeMapping);

        try {

            Channel channel = rabbitMQOperation.createChannel(publisherConnection);
            rabbitMQOperation.decalareExchangeAndBindQueue(channel,"shepherd_exchange","first-queue","routingKey", BuiltinExchangeType.DIRECT,true,6000);
            rabbitMQOperation.publishMessage(channel, "shepherd_exchange", "routingKey", JSONLoader.stringify(rootNode));

        } catch (Exception e) {

            log.error(String.format("Execution : %s failed.", executeWorkflowRequest.getExecutionId()), e);
            executeWorkflowRequest.setWorkflowExecutionState(WorkflowExecutionState.FAILED);
            executeWorkflowRequest.setUpdatedAt(DateUtil.currentDate());
            executeWorkflowRequest.setErrorMessage(e.getLocalizedMessage());
            workflowOperationDao.updateExecutionStatus(executeWorkflowRequest.getObjectId(), executeWorkflowRequest.getExecutionId(),
                    executeWorkflowRequest.getWorkflowExecutionState(), executeWorkflowRequest.getErrorMessage());

        }
    }
}
