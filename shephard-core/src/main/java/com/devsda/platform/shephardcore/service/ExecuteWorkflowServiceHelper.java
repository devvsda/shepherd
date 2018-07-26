package com.devsda.platform.shephardcore.service;

import com.devsda.platform.shephardcore.constants.NodeState;
import com.devsda.platform.shephardcore.model.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class ExecuteWorkflowServiceHelper {

    private static final Logger log = LoggerFactory.getLogger(ExecuteWorkflowServiceHelper.class);

    public static Boolean isNodeReadyToExecute(String nodeName, Map<String, List<String>> nodeToParentNodeMapping, Map<String, Node> nameToNodeMapping) {

        log.info(String.format("Checking weather node : %s is ready to execute", nodeName));

        List<String> parentNodeNames = nodeToParentNodeMapping.get(nodeName);

        for(String parent : parentNodeNames) {

            Node parentNode = nameToNodeMapping.get(parent);

            if( !NodeState.COMPLETED.equals(parentNode.getNodeState())) {
                return Boolean.FALSE;
            }
        }

        return Boolean.TRUE;
    }
}
