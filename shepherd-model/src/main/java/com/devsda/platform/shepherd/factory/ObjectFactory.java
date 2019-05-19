package com.devsda.platform.shepherd.factory;

import com.devsda.platform.shepherd.constants.NodeState;
import com.devsda.platform.shepherd.model.Node;

public class ObjectFactory {

    public static Node createNode(String nodeName, String objectId, String executionId, NodeState status, String errorMessage) {

        Node node = new Node();
        node.setName(nodeName);
        node.setObjectId(objectId);
        node.setExecutionId(executionId);
        node.setNodeState(status);
        node.setErrorMessage(errorMessage);

        return node;
    }
}
