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

    public Boolean isNodeReadyToExecute(String nodeName, Map<String, List<String>> nodeToParentNodeMapping, Map<String, Node> nameToNodeMapping) {

        log.info(String.format("Checking weather node : %s is ready to execute", nodeName));

        Node thisNode = nameToNodeMapping.get(nodeName);

        if (thisNode.getNodeId() != null) {
            log.info(String.format("Node : %s is already processed by other parent.", nodeName));
            return Boolean.FALSE;
        }

        List<String> parentNodeNames = nodeToParentNodeMapping.get(nodeName);

        for (String parent : parentNodeNames) {

            Node parentNode = nameToNodeMapping.get(parent);

            Node parentNodeDao = workflowOperationDao.getNode(parentNode.getName(), parentNode.getObjectId(), parentNode.getExecutionId());

            log.info(String.format("Node state as per DAO : %s", parentNodeDao));

            if (parentNodeDao == null || !NodeState.COMPLETED.equals(parentNodeDao.getNodeState())) {
                log.info(String.format("Node : %s is not ready to execute.", nodeName));
                return Boolean.FALSE;
            }
        }

        log.info(String.format("Node : %s is ready to execute.", nodeName));
        return Boolean.TRUE;
    }
}
