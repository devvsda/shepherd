package com.devsda.platform.shepherdcore.service;

import com.devsda.platform.shepherdcore.dao.RegisterationDao;
import com.devsda.platform.shepherdcore.dao.WorkflowOperationDao;
import com.devsda.platform.shepherd.model.*;
import com.devsda.platform.shepherdcore.service.documentservice.ExecutionDocumentService;
import com.devsda.platform.shepherdcore.util.RequestValidator;
import com.devsda.platform.shepherd.exception.ClientInvalidRequestException;
import com.devsda.platform.shepherd.graphgenerator.DAGGenerator;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class ClientDataRetrievelService {

    private static final Logger log = LoggerFactory.getLogger(ClientDataRetrievelService.class);

    @Inject
    private RegisterationDao registerationDao;

    @Inject
    private DAGGenerator dagGenerator;

    @Inject
    private WorkflowOperationDao workflowOperationDao;


    @Inject
    private ExecutionDocumentService executionDocumentService;

    public List<ClientDetails> getAllClients() {

        return registerationDao.getAllClientDetails();
    }

    public ClientDetails getClientDetails(String clientName) {

        return registerationDao.getClientDetails(clientName);
    }


    public List<EndpointDetails> getAllEndpoints(String clientName) throws IOException {

        ClientDetails clientDetails = registerationDao.getClientDetails(clientName);

        if (clientDetails == null) {
            throw new ClientInvalidRequestException("Invalid client name.");
        }

        int clientId = clientDetails.getClientId();


        List<EndpointDetails> endpointDetails = executionDocumentService.fetchAllEndPointDetails(clientId);

        return endpointDetails;
    }

    public EndpointDetails getEndpointDetails(String clientName, String endpointName) throws IOException {

        ClientDetails clientDetails = registerationDao.getClientDetails(clientName);

        if (clientDetails == null) {
            throw new ClientInvalidRequestException("Invalid client name.");
        }

        int clientId = clientDetails.getClientId();

        EndpointDetails endpointDetails = executionDocumentService.fetchEndPointDetails(clientId, endpointName);

        return endpointDetails;
    }

    public Graph getGraphJSON(String clientName, String endpointName) throws Exception {

        ClientDetails clientDetails = registerationDao.getClientDetails(clientName);

        if (clientDetails == null) {
            throw new ClientInvalidRequestException("Invalid client name.");
        }

        int clientId = clientDetails.getClientId();


        EndpointDetails endpointDetails = executionDocumentService.fetchEndPointDetails(clientId, endpointName);

        String stringifyGRaph = endpointDetails.getDAGGraph();


        Graph graph = dagGenerator.generateFromString(stringifyGRaph);

        return graph;
    }

    public ExecutionDetails getExecutionState(String clientName, String endpointName, String objectId, String executionId) {

        ClientDetails clientDetails = RequestValidator.validateClient(clientName);
        RequestValidator.validateEndpoint(clientDetails.getClientId(), clientName, endpointName);
        ExecutionDetails executionDetails = RequestValidator.validateExecution(
                clientName, endpointName, objectId, executionId);


        List<Node> nodes = workflowOperationDao.getAllNodes(objectId, executionId);
        executionDetails.setNodes(nodes);

        return executionDetails;
    }

    public List<ExecutionDetails> getAllExecutions(String clientName, String endpointName) {

        ClientDetails clientDetails = RequestValidator.validateClient(clientName);
        EndpointDetails endpointDetails = RequestValidator.validateEndpoint(clientDetails.getClientId(), clientName, endpointName);

        List<ExecutionDetails> executionDetails = workflowOperationDao.getAllExecutions(clientDetails.getClientId(), endpointDetails.getEndpointId());

        return executionDetails;
    }

}
