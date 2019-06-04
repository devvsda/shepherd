package com.devsda.platform.shepherdcore.resources;

import com.devsda.platform.shepherdcore.constants.ShephardConstants;
import com.devsda.platform.shepherdcore.service.ClientRegisterationService;
import com.devsda.platform.shepherd.model.EndpointRequest;
import com.devsda.platform.shepherd.model.RegisterClientRequest;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path(ShephardConstants.Resources.REGISTRATION)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClientRegisterationResources {

    @Inject
    public ClientRegisterationService clientRegisterationService;
    @Inject
    public ResourceHelper resourceHelper;
    private Logger log = LoggerFactory.getLogger(ClientRegisterationResources.class);

    /**
     * @param registerClientRequest
     * @return
     */
    @POST
    @Path(ShephardConstants.Resources.CLIENT)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerClient(@NotNull RegisterClientRequest registerClientRequest) {

        try {
            log.info(String.format("Processing register client for %s", registerClientRequest));

            Integer clientId = clientRegisterationService.registerClient(registerClientRequest);

            log.info(String.format("Processing successfully completed for register client for %s", registerClientRequest));

            Map<String, Object> shepherdResponse = new HashMap<>();
            shepherdResponse.put("ClientId", clientId);

            return Response.ok(resourceHelper.createShepherdResponse(com.devsda.platform.shepherd.constants.ResourceName.REGISTER_CLIENT, shepherdResponse, "Client registered successfully", null)).build();

        } catch (Throwable e) {

            return Response.ok(resourceHelper.createShepherdResponse(com.devsda.platform.shepherd.constants.ResourceName.REGISTER_CLIENT, null, null, e.getLocalizedMessage())).build();
        }

    }


    @POST
    @Path(ShephardConstants.Resources.ENDPOINT)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerEndpoint(@NotNull EndpointRequest registerEndpointRequest) {

        try {
            log.info(String.format("Processing register endpoint for %s", registerEndpointRequest));

            String endpointId = clientRegisterationService.registerEndpoint(registerEndpointRequest);

            log.info(String.format("Processing successfully completed for register client for %s", registerEndpointRequest));

            Map<String, Object> shepherdResponse = new HashMap<>();
            shepherdResponse.put("endpointId", endpointId);

            return Response.ok(resourceHelper.createShepherdResponse(com.devsda.platform.shepherd.constants.ResourceName.REGISTER_ENDPOINT, shepherdResponse, "Registered endpoint successfully", null)).build();

        } catch (Throwable e) {

            return Response.ok(resourceHelper.createShepherdResponse(com.devsda.platform.shepherd.constants.ResourceName.REGISTER_ENDPOINT, null, null, e.getLocalizedMessage())).build();
        }

    }
}
