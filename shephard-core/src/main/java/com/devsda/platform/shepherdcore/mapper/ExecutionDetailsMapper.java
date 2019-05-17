package com.devsda.platform.shepherdcore.mapper;

import com.devsda.platform.shepherd.model.ExecutionDetails;
import com.devsda.platform.shepherd.constants.WorkflowExecutionState;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ExecutionDetailsMapper implements ResultSetMapper<ExecutionDetails> {

    @Override
    public ExecutionDetails map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {

        ExecutionDetails executionDetails = new ExecutionDetails();

        executionDetails.setObjectId(resultSet.getString("object_id"));
        executionDetails.setExecutionId(resultSet.getString("execution_id"));
        executionDetails.setClientId(resultSet.getInt("client_id"));
        executionDetails.setEndpointId(resultSet.getInt("endpoint_id"));
        executionDetails.setCreatedAt(resultSet.getDate("created_at"));
        executionDetails.setUpdatedAt(resultSet.getDate("updated_at"));
        executionDetails.setCreatedBy(resultSet.getString("created_by"));
        executionDetails.setErrorMessage(resultSet.getString("error_message"));
        executionDetails.setWorkflowExecutionState(WorkflowExecutionState.valueOf(resultSet.getString("status")));

        return executionDetails;
    }
}
