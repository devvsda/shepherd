package com.devsda.platform.shephardcore.dao;


import com.devsda.platform.shephardcore.mapper.ExecutionDetailsMapper;
import com.devsda.platform.shephardcore.mapper.NodeDetailsMapper;
import com.devsda.platform.shepherd.model.ExecutionDetails;
import com.devsda.platform.shepherd.constants.WorkflowExecutionState;
import com.devsda.platform.shepherd.model.ExecuteWorkflowRequest;
import com.devsda.platform.shepherd.model.Node;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

public interface WorkflowOperationDao {

    @SqlUpdate("insert into execution_details(object_id, execution_id, client_id, endpoint_id, status, created_at, updated_at, created_by) values(:executeWorkflowRequest.objectId, :executeWorkflowRequest.executionId, :executeWorkflowRequest.clientId, :executeWorkflowRequest.endpointId, :executeWorkflowRequest.workflowExecutionState, :executeWorkflowRequest.createdAt, :executeWorkflowRequest.updatedAt, :executeWorkflowRequest.submittedBy)")
    public int executeWorkflow(@BindBean("executeWorkflowRequest") ExecuteWorkflowRequest executeWorkflowRequest);


    @SqlUpdate("update execution_details set status=:workflowState, error_message=:errorMessage  where object_id = :objectId and execution_id = :executionId")
    public int updateExecutionStatus(@Bind("objectId") String objectId, @Bind("executionId") String executionId, @Bind("workflowState") WorkflowExecutionState workflowExecutionState, @Bind("errorMessage") String errorMessage);

    @RegisterMapper(ExecutionDetailsMapper.class)
    @SqlQuery("select * from execution_details where object_id = :objectId and execution_id = :executionId")
    public ExecutionDetails getExecutionDetails(@Bind("objectId") String objectId, @Bind("executionId") String executionId);

    @SqlUpdate("update execution_details set processed_nodes=:processedNodes.toString(), inProcessingNodes=:inProcessingNodes.toString() where execution_id = :executionId")
    public int updateNodeStatus(@Bind("executionId") Integer executionId, @Bind("processedNodes") List<Integer> processedNodes, @Bind("inProcessingNodes") List<Integer> inProcessingNodes);


    @SqlUpdate("insert into node_details(node_name, object_id, execution_id, status, error_message, created_at, updated_at, created_by) values(:node.name, :node.objectId, :node.executionId,:node.nodeState,:node.errorMessage,:node.createdAt,:node.updatedAt,:node.submittedBy)")
    public int createNode(@BindBean("node") Node node);

    @SqlUpdate("update node_details set status = :node.nodeState, error_message = :node.errorMessage, updated_at = :node.updatedAt where execution_id = :node.executionId and node_name = :node.name")
    public int updateNode(@BindBean("node") Node node);

    @RegisterMapper(NodeDetailsMapper.class)
    @SqlQuery("select * from node_details where object_id = :objectId and execution_id = :executionId and node_name = :nodeName")
    public Node getNode(@Bind("nodeName") String nodeName, @Bind("objectId") String objectId, @Bind("executionId") String executionId);

    @RegisterMapper(NodeDetailsMapper.class)
    @SqlQuery("select * from node_details where object_id = :objectId and execution_id = :executionId")
    public List<Node> getAllNodes(@Bind("objectId") String objectId, @Bind("executionId") String executionId);
}
