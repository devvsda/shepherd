package com.devsda.platform.shephardcore.service;

import com.devsda.platform.shephardcore.dao.RegisterationDao;
import com.devsda.platform.shepherd.exception.ClientInvalidRequestException;
import com.devsda.platform.shepherd.graphgenerator.DAGGenerator;
import com.devsda.platform.shephardcore.model.ClientDetails;
import com.devsda.platform.shephardcore.model.EndpointDetails;
import com.devsda.platform.shepherd.model.Graph;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ClientDataRetrievelService {

    private static final Logger log = LoggerFactory.getLogger(ClientDataRetrievelService.class);

    @Inject
    public RegisterationDao registerationDao;

    @Inject
    public DAGGenerator dagGenerator;

    public List<ClientDetails> getAllClients() {

        return registerationDao.getAllClientDetails();
    }


    public List<EndpointDetails> getAllEndpoints(String clientName) {

        ClientDetails clientDetails = registerationDao.getClientDetails(clientName);

        if(clientDetails == null) {
            throw new ClientInvalidRequestException("Invalid client name.");
        }

        int clientId = clientDetails.getClientId();


        List<EndpointDetails> endpointDetails = registerationDao.getAllEndpoints(clientId);

        return endpointDetails;
    }

    public EndpointDetails getEndpointDetails(String clientName, String endpointName) {

        ClientDetails clientDetails = registerationDao.getClientDetails(clientName);

        if(clientDetails == null) {
            throw new ClientInvalidRequestException("Invalid client name.");
        }

        int clientId = clientDetails.getClientId();

        EndpointDetails endpointDetails = registerationDao.getEndpointDetails(clientName, endpointName);

        return endpointDetails;
    }

    public Graph getGraphJSON(String clientName, String endpointName) throws Exception {

        ClientDetails clientDetails = registerationDao.getClientDetails(clientName);

        if(clientDetails == null) {
            throw new ClientInvalidRequestException("Invalid client name.");
        }

        int clientId = clientDetails.getClientId();


        EndpointDetails endpointDetails = registerationDao.getEndpointDetails(clientName, endpointName);

        String stringifyGRaph = endpointDetails.getDAGGraph();


        Graph graph = dagGenerator.generateFromString(stringifyGRaph);

        return graph;
    }

}
