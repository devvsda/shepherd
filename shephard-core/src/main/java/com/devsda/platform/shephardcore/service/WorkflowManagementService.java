package com.devsda.platform.shephardcore.service;

import com.devsda.platform.shephardcore.dao.WorkflowOperationDao;
import com.devsda.platform.shephardcore.model.ClientDetails;
import com.devsda.platform.shephardcore.util.RequestValidator;
import com.devsda.platform.shepherd.constants.WorkflowExecutionState;
import com.devsda.platform.shepherd.model.ExecuteWorkflowRequest;
import com.devsda.platform.shepherd.model.WorkflowManagementRequest;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkflowManagementService {

    private static final Logger log = LoggerFactory.getLogger(WorkflowOperationDao.class);

    @Inject
    private WorkflowOperationDao workflowOperationDao;

    @Inject
    private ExecuteWorkflowService executeWorkflowService;

    public void killWorkflow(WorkflowManagementRequest killWorkflowRequest) {


        ClientDetails clientDetails = RequestValidator.validateClient(killWorkflowRequest.getClientName());
        RequestValidator.validateEndpoint(clientDetails.getClientId(), killWorkflowRequest.getClientName(), killWorkflowRequest.getEndpointName());
        RequestValidator.validateExecution(
                killWorkflowRequest.getClientName(), killWorkflowRequest.getEndpointName(), killWorkflowRequest.getExecutionId());

        log.debug(String.format("Updating status of execution id : %s with value : %s", killWorkflowRequest.getExecutionId(), WorkflowExecutionState.KILLED.name()));

        workflowOperationDao.updateExecutionStatus(killWorkflowRequest.getExecutionId(), WorkflowExecutionState.KILLED, null);
    }

    public void resumeWorkflow(WorkflowManagementRequest resumeWorkflowRequest) {

        ClientDetails clientDetails = RequestValidator.validateClient(resumeWorkflowRequest.getClientName());
        RequestValidator.validateEndpoint(clientDetails.getClientId(), resumeWorkflowRequest.getClientName(), resumeWorkflowRequest.getEndpointName());
        RequestValidator.validateExecution(
                resumeWorkflowRequest.getClientName(), resumeWorkflowRequest.getEndpointName(), resumeWorkflowRequest.getExecutionId());

    }

    public Integer restartWorkflow(WorkflowManagementRequest restartWorkflowRequest) throws Exception {

        killWorkflow(restartWorkflowRequest);

        ExecuteWorkflowRequest executeWorkflowRequest = new ExecuteWorkflowRequest();
        executeWorkflowRequest.setClientName(restartWorkflowRequest.getClientName());
        executeWorkflowRequest.setEndpointName(restartWorkflowRequest.getEndpointName());

        Integer newExecutionId = executeWorkflowService.executeWorkflow(executeWorkflowRequest);

        return newExecutionId;
    }
}
