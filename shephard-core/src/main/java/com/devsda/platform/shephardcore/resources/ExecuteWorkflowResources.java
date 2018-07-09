package com.devsda.platform.shephardcore.resources;

import com.devsda.platform.shephardcore.constants.ShephardConstants;
import com.devsda.platform.shephardcore.model.ExecuteWorkflowRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path(ShephardConstants.Resources.MANAGE)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ExecuteWorkflowResources {

    private static final Logger log = LoggerFactory.getLogger(ExecuteWorkflowResources.class);

    @POST
    @Path(ShephardConstants.Resources.EXECUTE)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response executeWorkflow(@NotNull ExecuteWorkflowRequest executeWorkflowRequest) {

        log.info(String.format("Processing execute request for %s", executeWorkflowRequest));

        // TODO : File to shared

        log.info(String.format("Successfully processed execute request for %s", executeWorkflowRequest));
        return null;

    }
}
