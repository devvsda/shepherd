package com.devsda.platform.shephardcore.dao;


import com.devsda.platform.shephardcore.mapper.ClientDetailsMapper;
import com.devsda.platform.shephardcore.mapper.EndpointDetailsMapper;
import com.devsda.platform.shephardcore.model.ClientDetails;
import com.devsda.platform.shephardcore.model.EndpointDetails;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

public interface RegisterationDao {

    @SqlUpdate("insert into client_details(client_name, created_at, updated_at, created_by) values(:clientDetails.clientName, :clientDetails.createdAt, :clientDetails.updatedAt, :clientDetails.submittedBy)")
    int registerClient(@BindBean("clientDetails") ClientDetails clientDetails);

    @RegisterMapper(ClientDetailsMapper.class)
    @SqlQuery("select * from client_details where client_name = :clientName")
    ClientDetails getClientDetails(@Bind("clientName") String clientName);

    @RegisterMapper(ClientDetailsMapper.class)
    @SqlQuery("select * from client_details")
    List<ClientDetails> getAllClientDetails();

    @SqlUpdate("insert into endpoint_details(client_id, endpoint_name, workflow_graph, endpoint_details, created_at, updated_at, created_by) values(:endpointDetail.clientId, :endpointDetail.endpointName, :endpointDetail.DAGGraph, :endpointDetail.endpointDetails, :endpointDetail.createdAt, :endpointDetail.updatedAt, :endpointDetail.submittedBy)")
    int registerEndpoint(@BindBean("endpointDetail")EndpointDetails endpointDetails);

    @RegisterMapper(EndpointDetailsMapper.class)
    @SqlQuery("select * from endpoint_details where client_id = :clientId")
    List<EndpointDetails> getAllEndpoints(@Bind("clientId") Integer clientId);

    @RegisterMapper(EndpointDetailsMapper.class)
    @SqlQuery("select * from endpoint_details where client_id = :clientId and endpoint_name = :endpointName")
    EndpointDetails getEndpointDetails(@Bind("clientId") Integer clientId, @Bind("endpointName") String endpointName);
}
