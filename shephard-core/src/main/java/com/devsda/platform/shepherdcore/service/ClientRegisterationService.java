package com.devsda.platform.shepherdcore.service;

import com.devsda.platform.shepherdcore.dao.RegisterationDao;
import com.devsda.platform.shepherd.model.ClientDetails;
import com.devsda.platform.shepherd.model.EndpointDetails;
import com.devsda.platform.shepherd.constants.ShepherdConstants;
import com.devsda.platform.shepherd.exception.ClientInvalidRequestException;
import com.devsda.platform.shepherd.model.EndpointRequest;
import com.devsda.platform.shepherd.model.RegisterClientRequest;
import com.devsda.platform.shepherd.util.DateUtil;
import com.devsda.platform.shepherdcore.service.documentservice.ExecutionDocumentService;
import com.google.inject.Inject;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class ClientRegisterationService {

    private static Logger log = LoggerFactory.getLogger(ClientRegisterationService.class);

    @Inject
    public RegisterationDao registerationDao;


    @Inject
    public ExecutionDocumentService executionDocumentService;

    public Integer registerClient(RegisterClientRequest registerClientRequest) {

        log.info(String.format("Processing register client for %s", registerClientRequest));

        ClientDetails clientDetails = convertToClientDetails(registerClientRequest);

        ClientDetails isItRegistered = registerationDao.getClientDetails(registerClientRequest.getClientName());

        Integer clientId = null;

        if (isItRegistered == null) {
            clientId = registerationDao.registerClient(clientDetails);
        } else {
            throw new ClientInvalidRequestException(String.format("Username %s already exists.", registerClientRequest.getClientName()));
        }

        return clientId;
    }

    public String registerEndpoint(EndpointRequest registerEndpointRequest) throws IOException {


        ClientDetails clientDetails = registerationDao.getClientDetails(registerEndpointRequest.getClientName());

        if (clientDetails == null) {
            throw new ClientInvalidRequestException("Invalid client name");
        }


        EndpointDetails endpointDetails = executionDocumentService.fetchEndPointDetails(clientDetails.getClientId(), registerEndpointRequest.getEndpointName());

        String endpointId = null;

        if (endpointDetails == null) {

            endpointDetails = convertToEndpointDetails(registerEndpointRequest);
            endpointDetails.setClientId(clientDetails.getClientId());

            endpointId = executionDocumentService.registerEndPointDetails(endpointDetails).toString();

        } else {
            throw new ClientInvalidRequestException(String.format("Endpoint with name : %s already exists for client %s",
                    registerEndpointRequest.getEndpointName(), registerEndpointRequest.getClientName()));
        }

        return endpointId;
    }


    private EndpointDetails convertToEndpointDetails(EndpointRequest registerEndpointRequest) {

        EndpointDetails endpointDetails = new EndpointDetails();
        endpointDetails.setEndpointName(registerEndpointRequest.getEndpointName());
        endpointDetails.setClientName(registerEndpointRequest.getClientName());
        endpointDetails.setDAGGraph(registerEndpointRequest.getDAGGraph());
        endpointDetails.setEndpointDetails(registerEndpointRequest.getEndpointDetails());
        endpointDetails.setCreatedAt(DateUtil.currentDate());
        endpointDetails.setUpdatedAt(DateUtil.currentDate());
        endpointDetails.setSubmittedBy(ShepherdConstants.PROCESS_OWNER);

        return endpointDetails;
    }

    private ClientDetails convertToClientDetails(RegisterClientRequest registerClientRequest) {

        ClientDetails clientDetails = new ClientDetails(registerClientRequest.getClientName(), ShepherdConstants.PROCESS_OWNER);
        clientDetails.setCreatedAt(DateUtil.currentDate());
        clientDetails.setUpdatedAt(DateUtil.currentDate());

        return clientDetails;

    }
}
