package com.devsda.platform.shephardcore.resources;

import com.devsda.platform.shephardcore.constants.ShephardConstants;
import com.devsda.platform.shephardcore.loader.JSONLoader;
import com.devsda.platform.shephardcore.model.ClientDetails;
import com.devsda.platform.shephardcore.model.EndpointDetails;
import com.devsda.platform.shephardcore.model.Graph;
import com.devsda.platform.shephardcore.service.ClientDataRetrievelService;
import com.google.inject.Inject;
import com.sun.org.apache.regexp.internal.RE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

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

            return Response.ok(resourceHelper.createShepherdResponse(null, clientDetails, null)).build();
        } catch(Throwable e) {
            return Response.ok(resourceHelper.createShepherdResponse(null, null, "Failed to fetch registered clients data")).build();
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

            return Response.ok(resourceHelper.createShepherdResponse(null, endpointDetails, null)).build();

        } catch(Throwable e) {

            return Response.ok(resourceHelper.createShepherdResponse(null, null, "Failed to fetch registered endpoints for given client data")).build();

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

            return Response.ok(resourceHelper.createShepherdResponse(null, endpointDetails, null)).build();

        } catch(Throwable e) {

            return Response.ok(resourceHelper.createShepherdResponse(null, null, "Failed to fetch registered endpoints for given client data")).build();

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

            return Response.ok(resourceHelper.createShepherdResponse(null, graph.getNodes(), null)).build();

        } catch(Throwable e) {

            log.error("Failed", e);

            return Response.ok(resourceHelper.createShepherdResponse(null, null, "Failed to convert graph xml into json data")).build();

        }

    }

}
