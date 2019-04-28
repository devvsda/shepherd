package com.devsda.platform.shephardcore.dao;

import com.devsda.platform.shephardcore.ApplicationContextUtil;
import com.devsda.platform.shepherd.constants.NodeState;
import com.devsda.platform.shepherd.constants.ShepherdConstants;
import com.devsda.platform.shepherd.factory.ObjectFactory;
import com.devsda.platform.shepherd.model.Node;
import com.google.inject.Injector;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class WorkflowOperationDaoTest {

    private static WorkflowOperationDao workflowOperationDao;

    @BeforeClass
    public static void setup() throws Exception {
        Injector injector = ApplicationContextUtil.createApplicationInjector();
        workflowOperationDao = injector.getInstance(WorkflowOperationDao.class);
    }

    @AfterClass
    public static void tearDown() {

    }

    @Test
    public void checkObjectTest() {
        Assert.assertNotNull(workflowOperationDao);
    }

    @Test
    public void createNodeTest() {

        Node node = ObjectFactory.createNode("NameTest", null, 1, NodeState.PROCESSING, null) ;
        node.setSubmittedBy(ShepherdConstants.PROCESS_OWNER);

        workflowOperationDao.createNode(node);
    }

    @Test
    public void updateNodeTest() {

        Node node = ObjectFactory.createNode("NameTest", null, 1, NodeState.FAILED, "TEST FAILURE") ;
        node.setSubmittedBy(ShepherdConstants.PROCESS_OWNER);
        node.setNodeId(1);

        workflowOperationDao.updateNode(node);

    }
}
