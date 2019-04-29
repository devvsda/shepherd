package com.devsda.platform.shephardcore.mapper;

import com.devsda.platform.shepherd.constants.NodeState;
import com.devsda.platform.shepherd.model.Node;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NodeDetailsMapper implements ResultSetMapper<Node> {

    @Override
    public Node map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {

        Node node = new Node();
        node.setName(resultSet.getString("node_name"));
        node.setNodeId(resultSet.getInt("node_id"));
        node.setExecutionId(resultSet.getInt("execution_id"));
        node.setNodeState(NodeState.valueOf(resultSet.getString("status")));
        node.setErrorMessage(resultSet.getString("error_message"));
        node.setCreatedAt(resultSet.getDate("created_at"));
        node.setUpdatedAt(resultSet.getDate("updated_at"));
        node.setSubmittedBy(resultSet.getString("created_by"));

        return node;
    }
}
