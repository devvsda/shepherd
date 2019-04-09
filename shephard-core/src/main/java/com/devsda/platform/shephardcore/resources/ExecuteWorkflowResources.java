package com.devsda.platform.shephardcore.resources;

import com.devsda.platform.shephardcore.constants.ShephardConstants;
import com.devsda.platform.shepherd.model.ExecuteWorkflowRequest;
import com.devsda.platform.shephardcore.service.ExecuteWorkflowService;
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

@Path(ShephardConstants.Resources.MANAGE)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ExecuteWorkflowResources {

    private static final Logger log = LoggerFactory.getLogger(ExecuteWorkflowResources.class);
    private ExecuteWorkflowService executeWorkflowService = new ExecuteWorkflowService();

    @Inject
    public ResourceHelper resourceHelper;


    @POST
    @Path(ShephardConstants.Resources.EXECUTE)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response executeWorkflow(@NotNull ExecuteWorkflowRequest executeWorkflowRequest) {

        try {
            log.info(String.format("Processing execute request for %s", executeWorkflowRequest));

            Integer executionId = executeWorkflowService.executeWorkflow(executeWorkflowRequest);

            Map<String, Object> shepherdResponse = new HashMap<>();
            shepherdResponse.put("execution_id", executionId);

            log.info(String.format("Successfully started processing  execute request for %s", executeWorkflowRequest));

            return Response.ok(resourceHelper.createShepherdResponse(com.devsda.platform.shepherd.constants.ResourceName.REGISTER_CLIENT, shepherdResponse,"Stored successfully", null)).build();

        } catch(Throwable e) {

            return Response.ok(resourceHelper.createShepherdResponse(com.devsda.platform.shepherd.constants.ResourceName.REGISTER_CLIENT, null, null, e.getLocalizedMessage())).build();

        }

    }
}
