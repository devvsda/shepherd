package com.devsda.platform.shephardcore.resources;

import com.devsda.platform.shephardcore.constants.ShephardConstants;
import com.devsda.platform.shepherd.model.ClientDetails;
import com.devsda.platform.shepherd.model.EndpointDetails;
import com.devsda.platform.shephardcore.service.ClientDataRetrievelService;
import com.devsda.platform.shepherd.constants.ResourceName;
import com.devsda.platform.shepherd.model.ExecutionDetails;
import com.devsda.platform.shepherd.model.Graph;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path(ShephardConstants.Resources.RETRIEVE)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClientDataRetrievalResources {

    private static final Logger log = LoggerFactory.getLogger(ClientDataRetrievalResources.class);

    @Inject
    public ResourceHelper resourceHelper;

    @Inject
    public ClientDataRetrievelService clientDataRetrievelService;

    @GET
    @Path(ShephardConstants.Resources.CLIENTS)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAllRegisteredClients() {

        try {

            log.info(String.format("PRocessing get all clients data"));

            List<ClientDetails> clientDetails = clientDataRetrievelService.getAllClients();

            log.info(String.format("Successfully completed get all clients data"));

            Map<String, Object> shepherdResponse = new HashMap<>();
            shepherdResponse.put("registered_clients", clientDetails);

            return Response.ok(resourceHelper.createShepherdResponse(ResourceName.RETRIEVE_REGISTERED_CLIENTS, shepherdResponse, "Successfully retrieved registered clients", null)).build();
        } catch (Throwable e) {
            return Response.ok(resourceHelper.createShepherdResponse(ResourceName.RETRIEVE_REGISTERED_CLIENTS, null, null, "Failed to retrieve registered clients")).build();
        }

    }

    @GET
    @Path(ShephardConstants.Resources.CLIENT)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getClient(@QueryParam("clientName") String clientName) {

        try {

            log.info(String.format("PRocessing Get Clients Data"));

            ClientDetails clientDetails = clientDataRetrievelService.getClientDetails(clientName);

            log.info(String.format("Successfully completed Get Client Details data"));

            Map<String, Object> shepherdResponse = new HashMap<>();
            shepherdResponse.put("client_details", clientDetails);

            return Response.ok(resourceHelper.createShepherdResponse(ResourceName.RETRIEVE_CLIENT_DETAILS, shepherdResponse, "Successfully retrieved client details", null)).build();
        } catch (Throwable e) {
            return Response.ok(resourceHelper.createShepherdResponse(ResourceName.RETRIEVE_CLIENT_DETAILS, null, null, "Failed to retrieve client details")).build();
        }

    }

    @GET
    @Path(ShephardConstants.Resources.ENDPOINTS)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAllRegisteredEndpoints(@QueryParam("clientName") String clientName) {

        try {

            log.info(String.format("Processing get all endpoints for given client : %s", clientName));

            List<EndpointDetails> endpointDetails = clientDataRetrievelService.getAllEndpoints(clientName);

            log.info(String.format("Successfully completed get all endpoints for given client %s", clientName));

            Map<String, Object> shepherdResponse = new HashMap<>();
            shepherdResponse.put("registered_endpoints", endpointDetails);

            return Response.ok(resourceHelper.createShepherdResponse(ResourceName.RETRIEVE_REGISTERED_ENDPOINTS, shepherdResponse, String.format("Successfully retrieved registered endpoints for client : %s", clientName), null)).build();

        } catch (Throwable e) {

            return Response.ok(resourceHelper.createShepherdResponse(ResourceName.RETRIEVE_REGISTERED_ENDPOINTS, null, null, String.format("Failed to retrieve registered endpoints for client : %s", clientName))).build();

        }

    }

    @GET
    @Path(ShephardConstants.Resources.ENDPOINT)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getEndpointDetails(@QueryParam("clientName") String clientName, @QueryParam("endpointName") String endpointName) {

        try {

            log.info(String.format("Processing get given endpoint for given client : %s", clientName));

            EndpointDetails endpointDetails = clientDataRetrievelService.getEndpointDetails(clientName, endpointName);

            log.info(String.format("Successfully completed get given endpoint for given client %s", clientName));

            Map<String, Object> shepherdResponse = new HashMap<>();
            shepherdResponse.put("endpoint", endpointDetails);

            return Response.ok(resourceHelper.createShepherdResponse(ResourceName.RETRIEVE_ENDPOINT, shepherdResponse, "Successfully retrieved endpoint details", null)).build();

        } catch (Throwable e) {

            return Response.ok(resourceHelper.createShepherdResponse(ResourceName.RETRIEVE_ENDPOINT, null, null, "Failed to retrieve endpoint details")).build();

        }

    }


    @GET
    @Path(ShephardConstants.Resources.GRAPH_DETAILS)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getGraphJSON(@QueryParam("clientName") String clientName, @QueryParam("endpointName") String endpointName) {

        try {

            log.info(String.format("Processing get graph JSON : %s", clientName));

            Graph graph = clientDataRetrievelService.getGraphJSON(clientName, endpointName);

            log.info(String.format("Successfully completed get graph JSON %s", clientName));

            Map<String, Object> shepherdResponse = new HashMap<>();
            shepherdResponse.put("graph", graph.getNodes());

            return Response.ok(resourceHelper.createShepherdResponse(ResourceName.GET_GRAPH, shepherdResponse, "Successfully retrieved graph details", null)).build();

        } catch (Throwable e) {

            return Response.ok(resourceHelper.createShepherdResponse(ResourceName.GET_GRAPH, null, null, "Failed to retrieve graph details")).build();

        }

    }

    @GET
    @Path(ShephardConstants.Resources.EXECUTION_STATE)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getExecutionState(@QueryParam("clientName") String clientName, @QueryParam("endpointName") String endpointName,
                                      @QueryParam("objectId") String objectId, @QueryParam("executionId") String executionId) {


        try {

            log.info(String.format("Processing Get Execution State Request for client : %s, endpoint : %s, objectId : %s, executionId : %s",
                    clientName, endpointName, objectId, executionId));

            ExecutionDetails executionDetails = clientDataRetrievelService.getExecutionState(clientName, endpointName, objectId, executionId);

            log.info(String.format("Successfully completed get graph JSON %s", clientName));

            Map<String, Object> shepherdResponse = new HashMap<>();
            shepherdResponse.put("executionDetails", executionDetails);

            return Response.ok(resourceHelper.createShepherdResponse(ResourceName.GET_EXECUTION_STATE, shepherdResponse, "Successfully retrieved execution state", null)).build();

        } catch (Throwable e) {

            log.error(String.format("Get Execution Details API failed for clientName : %s," +
                    " endpointName : %s, objectId : %s, executionId : %s", clientName, endpointName, objectId, executionId), e);
            return Response.ok(resourceHelper.createShepherdResponse(ResourceName.GET_EXECUTION_STATE, null, null, "Failed to retrieve execution state")).build();

        }

    }

    @GET
    @Path(ShephardConstants.Resources.GET_ALL_EXECUTIONS)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAllExecutions(@QueryParam("clientName") String clientName, @QueryParam("endpointName") String endpointName) {


        try {

            log.info(String.format("Processing Get All Executions Request for client : %s, endpoint : %s",
                    clientName, endpointName));

            List<ExecutionDetails> executionDetails = clientDataRetrievelService.getAllExecutions(clientName, endpointName);

            log.info(String.format("Successfully fetched all executions for client : %s, endpoint : %s", clientName, endpointName));

            Map<String, Object> shepherdResponse = new HashMap<>();
            shepherdResponse.put("executionDetails", executionDetails);

            return Response.ok(resourceHelper.createShepherdResponse(ResourceName.GET_ALL_EXECUTIONS, shepherdResponse, "Successfully retrieved all executions for given client and endpoint.", null)).build();

        } catch (Throwable e) {

            log.error(String.format("Get Execution Details API failed for clientName : %s," +
                    " endpointName : %s", clientName, endpointName), e);
            return Response.ok(resourceHelper.createShepherdResponse(ResourceName.GET_ALL_EXECUTIONS, null, null, "Failed to retrieve all executions for given client, and endpoint.")).build();

        }

    }

}
