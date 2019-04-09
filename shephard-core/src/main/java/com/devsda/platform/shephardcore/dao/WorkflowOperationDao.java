package com.devsda.platform.shephardcore.dao;


import com.devsda.platform.shepherd.constants.WorkflowExecutionState;
import com.devsda.platform.shepherd.model.ExecuteWorkflowRequest;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

import java.util.List;

public interface WorkflowOperationDao {

    @SqlUpdate("insert into execution_details(client_id, endpoint_id, status, created_at, updated_at, created_by) values(:executeWorkflowRequest.clientId, :executeWorkflowRequest.endpointId, :executeWorkflowRequest.status, :executeWorkflowRequest.createdAt, :executeWorkflowRequest.updatedAt, :executeWorkflowRequest.submittedBy)")
    @GetGeneratedKeys
    public int executeWorkflow( @BindBean("executeWorkflowRequest") ExecuteWorkflowRequest executeWorkflowRequest);


    @SqlUpdate("update execution_details set status=:workflowState.value() where execution_id = :executionId")
    public int updateExecutionStatus(@Bind("executionId") Integer executionId, @Bind("workflowState") WorkflowExecutionState workflowExecutionState);


    @SqlUpdate("update execution_details set processed_nodes=:processedNodes.toString(), inProcessingNodes=:inProcessingNodes.toString() where execution_id = :executionId")
    public int updateNodeStatus(@Bind("executionId") Integer executionId, @Bind("processedNodes") List<Integer> processedNodes, @Bind("inProcessingNodes") List<Integer> inProcessingNodes);
}
