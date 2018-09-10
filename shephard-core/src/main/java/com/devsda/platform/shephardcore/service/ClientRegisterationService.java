package com.devsda.platform.shephardcore.service;

import com.devsda.platform.shephardcore.constants.ShephardConstants;
import com.devsda.platform.shephardcore.dao.RegisterationDao;
import com.devsda.platform.shephardcore.exception.ClientInvalidRequestException;
import com.devsda.platform.shephardcore.model.ClientDetails;
import com.devsda.platform.shephardcore.model.EndpointDetails;
import com.devsda.platform.shephardcore.model.RegisterEndpointRequest;
import com.devsda.platform.shephardcore.util.DateUtil;
import com.devsda.platform.shepherd.model.RegisterClientRequest;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ClientRegisterationService {

    private static Logger log = LoggerFactory.getLogger(ClientRegisterationService.class);

    @Inject
    public RegisterationDao registerationDao;

    public void registerClient(RegisterClientRequest registerClientRequest) {

        log.info(String.format("Processing register client for %s", registerClientRequest));

        ClientDetails clientDetails = convertToClientDetails(registerClientRequest);

        ClientDetails isItRegistered = registerationDao.getClientDetails(registerClientRequest.getClientName());

        if(isItRegistered == null) {
            registerationDao.registerClient(clientDetails);
        } else {
            throw new ClientInvalidRequestException(String.format("Username %s already exists.", registerClientRequest.getClientName()));
        }
    }

    public void registerEndpoint(RegisterEndpointRequest registerEndpointRequest) {


        ClientDetails clientDetails = registerationDao.getClientDetails(registerEndpointRequest.getClientName());

        if(clientDetails == null) {
            throw new ClientInvalidRequestException("Invalid clienrt name");
        }

        EndpointDetails endpointDetails = convertToEndpointDetails(registerEndpointRequest);
        endpointDetails.setClientId(clientDetails.getClientId());

        registerationDao.registerEndpoint(endpointDetails);

    }


    private EndpointDetails convertToEndpointDetails(RegisterEndpointRequest registerEndpointRequest) {

        EndpointDetails endpointDetails = new EndpointDetails();
        endpointDetails.setEndpointName(registerEndpointRequest.getEndpointName());
        endpointDetails.setClientName(registerEndpointRequest.getClientName());
        endpointDetails.setDAGGraph(registerEndpointRequest.getDAGGraph());
        endpointDetails.setEndpointDetails(registerEndpointRequest.getEndpointDetails());
        endpointDetails.setCreatedAt(DateUtil.currentDate());
        endpointDetails.setUpdatedAt(DateUtil.currentDate());
        endpointDetails.setSubmittedBy(ShephardConstants.PROCESS_OWNER);

        return endpointDetails;
    }

    private ClientDetails convertToClientDetails(RegisterClientRequest registerClientRequest) {

        ClientDetails clientDetails = new ClientDetails(registerClientRequest.getClientName(), ShephardConstants.PROCESS_OWNER);
        clientDetails.setCreatedAt(DateUtil.currentDate());
        clientDetails.setUpdatedAt(DateUtil.currentDate());

        return clientDetails;

    }
}
