package com.devsda.platform.shephardcore.service;

import com.devsda.platform.shephardcore.dao.WorkflowOperationDao;
import com.devsda.platform.shepherd.constants.NodeState;
import com.devsda.platform.shepherd.model.Node;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class ExecuteWorkflowServiceHelper {

    private static final Logger log = LoggerFactory.getLogger(ExecuteWorkflowServiceHelper.class);

    @Inject
    private WorkflowOperationDao workflowOperationDao;

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
}
