package com.devsda.platform.shephardcore.service;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ExecuteWorkflowServiceTest {

    private static ExecuteWorkflowService executeWorkflowService;

    @BeforeClass
    public static void setup() {
        executeWorkflowService = new ExecuteWorkflowService();
    }

    @AfterClass
    public static void tearDown() {

    }

    @Test
    public void executeWorkflowTest() throws Exception {
        executeWorkflowService.executeWorkflow(null, null, null);
    }
}
