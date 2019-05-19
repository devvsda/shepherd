package com.devsda.platform.shephardcore.dao;

import com.devsda.platform.shephardcore.ApplicationContextUtil;
import com.devsda.platform.shepherd.model.ExecutionDetails;
import com.devsda.platform.shepherd.constants.NodeState;
import com.devsda.platform.shepherd.constants.ShepherdConstants;
import com.devsda.platform.shepherd.constants.WorkflowExecutionState;
import com.devsda.platform.shepherd.factory.ObjectFactory;
import com.devsda.platform.shepherd.model.ExecuteWorkflowRequest;
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

        Node node = ObjectFactory.createNode("NameTest", "objectId", "executionId",  NodeState.PROCESSING, null);
        node.setSubmittedBy(ShepherdConstants.PROCESS_OWNER);
        workflowOperationDao.createNode(node);

    }

    @Test
    public void updateNodeTest() {

        Node node = ObjectFactory.createNode("NameTest", "objectId", "executionId", NodeState.FAILED, "TEST FAILURE");
        node.setSubmittedBy(ShepherdConstants.PROCESS_OWNER);

        workflowOperationDao.updateNode(node);

    }

    @Test
    public void getNodeTest() {

        Node node = workflowOperationDao.getNode("NameTest", "objectId", "executionId");
        System.out.println(node);
    }

    @Test
    public void createExecutionTest() {

        ExecuteWorkflowRequest executionDetails = new ExecuteWorkflowRequest();

        executionDetails.setClientId(1);
        executionDetails.setEndpointId(1);
        executionDetails.setWorkflowExecutionState(WorkflowExecutionState.COMPLETED);
        executionDetails.setSubmittedBy("hitesh-jhamb");

        workflowOperationDao.executeWorkflow(executionDetails);

    }

    @Test
    public void updateExecutionStatusTest() {

        workflowOperationDao.updateExecutionStatus("objectId", "executionId", WorkflowExecutionState.FAILED, "INtentional");
    }

    @Test
    public void getExecutionDetailsTest() {

        ExecutionDetails executionDetails = workflowOperationDao.getExecutionDetails("objectId", "executionId");
        System.out.println(executionDetails);

    }
}
