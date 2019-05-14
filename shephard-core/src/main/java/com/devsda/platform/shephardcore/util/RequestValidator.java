package com.devsda.platform.shephardcore.util;

import com.devsda.platform.shephardcore.dao.RegisterationDao;
import com.devsda.platform.shephardcore.dao.WorkflowOperationDao;
import com.devsda.platform.shepherd.model.ClientDetails;
import com.devsda.platform.shepherd.model.EndpointDetails;
import com.devsda.platform.shepherd.model.ExecutionDetails;
import com.devsda.platform.shepherd.exception.ClientInvalidRequestException;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestValidator {

    private static final Logger log = LoggerFactory.getLogger(RequestValidator.class);

    @Inject
    private static RegisterationDao registerationDao;

    @Inject
    private static WorkflowOperationDao workflowOperationDao;

    public static ClientDetails validateClient(String clientName) {

        log.debug(String.format("Validating client details for client name : %s", clientName));

        if (clientName == null) {
            throw new ClientInvalidRequestException("ClientName should not be null");
        }

        ClientDetails clientDetails = registerationDao.getClientDetails(clientName);

        if (clientDetails == null) {
            throw new ClientInvalidRequestException(String.format("ClientName : %s does not exists.", clientName));
        }

        return clientDetails;

    }

    public static EndpointDetails validateEndpoint(Integer clientId, String clientName, String endpointName) {

        log.debug(String.format("Validating endpoint details for client name : %s, endpoint name : %s", clientName, endpointName));

        if (clientId == null || clientName == null || endpointName == null) {
            throw new ClientInvalidRequestException("Endpoint/ClientId/ClientName should not be null");
        }

        // Get endpoint details.
        EndpointDetails endpointDetails = registerationDao.getEndpointDetails(clientId, endpointName);

        if (endpointDetails == null) {
            log.error(String.format("Endpoint : %s is not present for client : %s",
                    endpointDetails.getEndpointName(), endpointDetails.getClientName()));
            throw new ClientInvalidRequestException(String.format("Client + Endpoint combination not registered." +
                    " ClientName : %s. EndpointName : %s", clientName, endpointName));
        }

        return endpointDetails;
    }

    public static ExecutionDetails validateExecution(String clientName, String endpointName, String objectId, String executionId) {

        log.debug(String.format("Validating execution details for client name : %s, endpoint name : %s, execution id : %s", clientName, endpointName, executionId));

        if (executionId == null) {
            throw new ClientInvalidRequestException("ExecutionId should not be null");
        }

        ExecutionDetails executionDetails = workflowOperationDao.getExecutionDetails(objectId, executionId);

        if (executionDetails == null) {
            log.error(String.format("Execution with id : %s not present for client : %s, and endpoint : %s",
                    executionId, clientName, endpointName));
            throw new ClientInvalidRequestException(String.format("Execution with id : %s not present for client : %s, and endpoint : %s",
                    executionId, clientName, endpointName));
        }

        return executionDetails;

    }

    public static void validateNode() {

    }
}
