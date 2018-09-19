package com.devsda.platform.shephardcore.resources;

import com.devsda.platform.shephardcore.constants.ShephardConstants;
import com.devsda.platform.shephardcore.model.ClientDetails;
import com.devsda.platform.shephardcore.model.EndpointDetails;
import com.devsda.platform.shepherd.constants.ResourceName;
import com.devsda.platform.shepherd.model.Graph;
import com.devsda.platform.shephardcore.service.ClientDataRetrievelService;
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
    @Path(ShephardConstants.Resources.CLIENT)
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
        } catch(Throwable e) {
            return Response.ok(resourceHelper.createShepherdResponse(ResourceName.RETRIEVE_REGISTERED_CLIENTS, null, null,"Failed to retrieve registered clients")).build();
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

        } catch(Throwable e) {

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

        } catch(Throwable e) {

            return Response.ok(resourceHelper.createShepherdResponse(ResourceName.RETRIEVE_ENDPOINT, null, null, "Failed to retrieve endpoint details")).build();

        }

    }


    @GET
    @Path(ShephardConstants.Resources.GRAPH_JSON)
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

        } catch(Throwable e) {

            return Response.ok(resourceHelper.createShepherdResponse(ResourceName.GET_GRAPH, null, null, "Failed to retrieve graph details")).build();

        }

    }

}
