package com.devsda.platform.shephardcore.resources;

import com.devsda.platform.shephardcore.constants.ResourceName;
import com.devsda.platform.shephardcore.constants.ShephardConstants;
import com.devsda.platform.shephardcore.model.RegisterClientRequest;
import com.devsda.platform.shephardcore.model.RegisterEndpointRequest;
import com.devsda.platform.shephardcore.service.ClientRegisterationService;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path(ShephardConstants.Resources.REGISTRATION)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClientRegisterationResources {

    private Logger log = LoggerFactory.getLogger(ClientRegisterationResources.class);

    @Inject
    public ClientRegisterationService clientRegisterationService;

    @Inject
    public ResourceHelper resourceHelper;


    @POST
    @Path(ShephardConstants.Resources.CLIENT)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerClient(@NotNull RegisterClientRequest registerClientRequest){

        try {
            log.info(String.format("Processing register client for %s", registerClientRequest));

            clientRegisterationService.registerClient(registerClientRequest);

            log.info(String.format("Processing successfully completed for register client for %s", registerClientRequest));

            return Response.ok(resourceHelper.createShepherdResponse(ResourceName.REGISTER_CLIENT, "Stored successfully", null)).build();

        } catch(Throwable e) {

            return Response.ok(resourceHelper.createShepherdResponse(ResourceName.REGISTER_CLIENT, null, e.getLocalizedMessage())).build();
        }

    }


    @POST
    @Path(ShephardConstants.Resources.ENDPOINT)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerEndpoint(@NotNull RegisterEndpointRequest registerEndpointRequest) {

        try {
            log.info(String.format("Processing register endpoint for %s", registerEndpointRequest));

            clientRegisterationService.registerEndpoint(registerEndpointRequest);

            log.info(String.format("Processing successfully completed for register client for %s", registerEndpointRequest));

            return Response.ok(resourceHelper.createShepherdResponse(ResourceName.REGISTER_ENDPOINT, "Registered endpoint successfully", null)).build();

        } catch(Throwable e) {

            return Response.ok(resourceHelper.createShepherdResponse(ResourceName.REGISTER_CLIENT, null, e.getLocalizedMessage())).build();
        }

    }

}
