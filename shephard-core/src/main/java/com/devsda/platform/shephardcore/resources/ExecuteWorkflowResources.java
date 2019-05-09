package com.devsda.platform.shephardcore.resources;

import com.devsda.platform.shephardcore.constants.ShephardConstants;
import com.devsda.platform.shephardcore.service.ExecuteWorkflowService;
import com.devsda.platform.shepherd.constants.ResourceName;
import com.devsda.platform.shepherd.model.ExecuteWorkflowRequest;
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

@Path(ShephardConstants.Resources.EXECUTE)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ExecuteWorkflowResources {

    private static final Logger log = LoggerFactory.getLogger(ExecuteWorkflowResources.class);
    @Inject
    public ResourceHelper resourceHelper;
    @Inject
    private ExecuteWorkflowService executeWorkflowService;

    @POST
    @Path(ShephardConstants.Resources.ENDPOINT)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response executeWorkflow(@NotNull ExecuteWorkflowRequest executeWorkflowRequest) {

        try {
            log.info(String.format("Processing execute request for %s", executeWorkflowRequest));

            Map<String, Object> executeWorkflowResponse = executeWorkflowService.executeWorkflow(executeWorkflowRequest);

            log.info(String.format("Successfully started processing  execute request for %s", executeWorkflowRequest));

            return Response.ok(resourceHelper.createShepherdResponse(
                    ResourceName.EXECUTE_WORKFLOW, executeWorkflowResponse, "Workflow triggered successfully.", null)).build();

        } catch (Throwable e) {

            log.error(String.format("Execute workflow failed for client : %s, and endpoint : %s", executeWorkflowRequest.getClientName(), executeWorkflowRequest.getEndpointName()), e);
            return Response.ok(resourceHelper.createShepherdResponse(com.devsda.platform.shepherd.constants.ResourceName.EXECUTE_WORKFLOW, null, "Workflow trigger failed.", e.getLocalizedMessage())).build();

        }

    }
}
