package com.devsda.platform.shephardcore.mapper;

import com.devsda.platform.shephardcore.model.ClientDetails;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientDetailsMapper implements ResultSetMapper<ClientDetails>{

    @Override
    public ClientDetails map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {

        ClientDetails clientDetails = new ClientDetails(resultSet.getString("client_name"), resultSet.getString("created_by"));
        clientDetails.setClientId(resultSet.getInt("client_id"));
        clientDetails.setCreatedAt(resultSet.getDate("created_at"));
        clientDetails.setUpdatedAt(resultSet.getDate("updated_at"));
        return clientDetails;

    }
}
