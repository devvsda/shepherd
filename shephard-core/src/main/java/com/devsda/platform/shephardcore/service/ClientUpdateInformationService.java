package com.devsda.platform.shephardcore.service;

import com.devsda.platform.shephardcore.dao.RegisterationDao;
import com.devsda.platform.shepherd.model.ClientDetails;
import com.devsda.platform.shepherd.model.EndpointDetails;
import com.devsda.platform.shepherd.exception.ClientInvalidRequestException;
import com.devsda.platform.shepherd.model.EndpointRequest;
import com.devsda.platform.shepherd.util.DateUtil;
import com.google.inject.Inject;

public class ClientUpdateInformationService {

    @Inject
    private RegisterationDao registerationDao;

    public void updateWorkflowDetails(EndpointRequest endpointRequest) {

        if (endpointRequest.getDAGGraph() == null) {
            throw new ClientInvalidRequestException("DAG field should not be null/empty.");
        }

        ClientDetails clientDetails = registerationDao.getClientDetails(endpointRequest.getClientName());

        if (clientDetails == null) {
            throw new ClientInvalidRequestException("Invalid client name");
        }

        EndpointDetails endpointDetails = registerationDao.getEndpointDetails(clientDetails.getClientId(), endpointRequest.getEndpointName());

        if (endpointDetails == null) {
            throw new ClientInvalidRequestException(String.format("Client : %s has not endpoint with name : %s",
                    endpointRequest.getClientName(), endpointRequest.getEndpointName()));
        }

        endpointDetails.setUpdatedAt(DateUtil.currentDate());
        endpointDetails.setDAGGraph(endpointRequest.getDAGGraph());
        registerationDao.updateWorkflowDetails(endpointDetails);

    }

    public void updateEndpointDetails(EndpointRequest endpointRequest) {

        if (endpointRequest.getEndpointDetails() == null) {
            throw new ClientInvalidRequestException("Endpoint Details field should not be null/empty.");
        }

        ClientDetails clientDetails = registerationDao.getClientDetails(endpointRequest.getClientName());

        if (clientDetails == null) {
            throw new ClientInvalidRequestException("Invalid client name");
        }

        EndpointDetails endpointDetails = registerationDao.getEndpointDetails(clientDetails.getClientId(), endpointRequest.getEndpointName());

        if (endpointDetails == null) {
            throw new ClientInvalidRequestException(String.format("Client : %s has not endpoint with name : %s",
                    endpointRequest.getClientName(), endpointRequest.getEndpointName()));
        }

        endpointDetails.setUpdatedAt(DateUtil.currentDate());
        endpointDetails.setEndpointDetails(endpointRequest.getEndpointDetails());
        registerationDao.updateEndpointDetails(endpointDetails);

    }
}
