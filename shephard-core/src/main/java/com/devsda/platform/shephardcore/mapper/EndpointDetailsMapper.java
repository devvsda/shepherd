package com.devsda.platform.shephardcore.mapper;

import com.devsda.platform.shephardcore.model.EndpointDetails;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import javax.xml.ws.Endpoint;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EndpointDetailsMapper implements ResultSetMapper<EndpointDetails> {

    @Override
    public EndpointDetails map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {

        EndpointDetails endpointDetails = new EndpointDetails();

        endpointDetails.setEndpointId(resultSet.getInt("endpoint_id"));
        endpointDetails.setEndpointName(resultSet.getString("endpoint_name"));


        endpointDetails.setClientId(resultSet.getInt("client_id"));

        endpointDetails.setDAGGraph(resultSet.getString("workflow_graph"));
        endpointDetails.setEndpointDetails(resultSet.getString("endpoint_details"));

        endpointDetails.setCreatedAt(resultSet.getDate("created_at"));
        endpointDetails.setUpdatedAt(resultSet.getDate("updated_at"));
        endpointDetails.setSubmittedBy(resultSet.getString("created_by"));

        return endpointDetails;
    }
}
