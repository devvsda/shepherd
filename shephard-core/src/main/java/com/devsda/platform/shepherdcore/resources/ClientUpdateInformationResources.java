package com.devsda.platform.shepherdcore.resources;

import com.devsda.platform.shepherdcore.constants.ShephardConstants;
import com.devsda.platform.shepherdcore.service.ClientUpdateInformationService;
import com.devsda.platform.shepherd.model.EndpointRequest;
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

@Path(ShephardConstants.Resources.UPDATE)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClientUpdateInformationResources {

    private static final Logger log = LoggerFactory.getLogger(ClientUpdateInformationResources.class);
    @Inject
    public ResourceHelper resourceHelper;
    @Inject
    private ClientUpdateInformationService clientUpdateInformationService;

    @POST
    @Path(ShephardConstants.Resources.ENDPOINT + ShephardConstants.Resources.WORKFLOW_DETAILS)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateWorkflowDetails(@NotNull EndpointRequest endpointRequest) {

        try {
            log.info(String.format("Processing update workflow details operation for %s", endpointRequest));

            clientUpdateInformationService.updateWorkflowDetails(endpointRequest);

            log.info(String.format("Processing successfully completed for update workflow details operation for %s", endpointRequest));

            return Response.ok(resourceHelper.createShepherdResponse(com.devsda.platform.shepherd.constants.ResourceName.UPDATE_ENDPOINT, null, "Update workflow details of endpoint successfully", null)).build();

        } catch (Throwable e) {

            return Response.ok(resourceHelper.createShepherdResponse(com.devsda.platform.shepherd.constants.ResourceName.UPDATE_ENDPOINT, null, null, e.getLocalizedMessage())).build();
        }

    }

    @POST
    @Path(ShephardConstants.Resources.ENDPOINT + ShephardConstants.Resources.ENDPOINT_DETAILS)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateEndpointDetails(@NotNull EndpointRequest endpointRequest) {

        try {
            log.info(String.format("Processing update workflow details operation for %s", endpointRequest));

            clientUpdateInformationService.updateEndpointDetails(endpointRequest);

            log.info(String.format("Processing successfully completed for update workflow details operation for %s", endpointRequest));

            return Response.ok(resourceHelper.createShepherdResponse(com.devsda.platform.shepherd.constants.ResourceName.UPDATE_ENDPOINT, null, "Update endpoint details of endpoint successfully", null)).build();

        } catch (Throwable e) {

            return Response.ok(resourceHelper.createShepherdResponse(com.devsda.platform.shepherd.constants.ResourceName.UPDATE_ENDPOINT, null, null, e.getLocalizedMessage())).build();
        }

    }
}
