package com.devsda.platform.shepherdcore.resources;

import com.devsda.platform.shepherdcore.constants.ShephardConstants;
import com.devsda.platform.shepherdcore.service.WorkflowManagementService;
import com.devsda.platform.shepherd.constants.ResourceName;
import com.devsda.platform.shepherd.model.WorkflowManagementRequest;
import com.google.inject.Inject;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

@Path(ShephardConstants.Resources.MANAGE + ShephardConstants.Resources.EXECUTION)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WorkflowManagementResources {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(WorkflowManagementResources.class);

    @Inject
    private WorkflowManagementService workflowManagementService;

    @Inject
    private ResourceHelper resourceHelper;

    @POST
    @Path(ShephardConstants.Resources.KILL)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response killWorkflow(@NotNull WorkflowManagementRequest killWorkflowRequest) {

        try {
            log.info(String.format("Processing Kill Execution Request for %s", killWorkflowRequest));

            workflowManagementService.killWorkflow(killWorkflowRequest);

            log.info(String.format("Successfully killed execution request for %s", killWorkflowRequest));

            return Response.ok(resourceHelper.createShepherdResponse(
                    ResourceName.KILL_EXECUTION, null,
                    "Execution killed successfully.", null))
                    .build();

        } catch (Throwable e) {

            log.error(String.format("Kill Execution failed for client : %s, endpoint : %s, and executionId : %s",
                    killWorkflowRequest.getClientName(), killWorkflowRequest.getEndpointName(),
                    killWorkflowRequest.getExecutionId()),
                    e);
            return Response.ok(resourceHelper.createShepherdResponse(
                    ResourceName.KILL_EXECUTION, null,
                    String.format("Kill Execution failed for client : %s, endpoint : %s, and executionId : %s",
                            killWorkflowRequest.getClientName(), killWorkflowRequest.getEndpointName(),
                            killWorkflowRequest.getExecutionId()), e.getLocalizedMessage()))
                    .build();

        }
    }

    @POST
    @Path(ShephardConstants.Resources.RESUME)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response resumeWorkflow(@NotNull WorkflowManagementRequest resumeWorkflowRequest) {

        try {
            log.info(String.format("Processing Resume Execution Request for %s", resumeWorkflowRequest));

            workflowManagementService.resumeWorkflow(resumeWorkflowRequest);

            log.info(String.format("Execution resumed successfully for %s", resumeWorkflowRequest));

            return Response.ok(resourceHelper.createShepherdResponse(
                    ResourceName.RESUME_EXECUTION, null,
                    "Execution resumed successfully.", null))
                    .build();

        } catch (Throwable e) {

            log.error(String.format("Resume Execution failed for client : %s, endpoint : %s, and executionId : %s",
                    resumeWorkflowRequest.getClientName(), resumeWorkflowRequest.getEndpointName(),
                    resumeWorkflowRequest.getExecutionId()),
                    e);
            return Response.ok(resourceHelper.createShepherdResponse(
                    ResourceName.RESUME_EXECUTION, null,
                    String.format("Resume Execution failed for client : %s, endpoint : %s, and executionId : %s",
                            resumeWorkflowRequest.getClientName(), resumeWorkflowRequest.getEndpointName(),
                            resumeWorkflowRequest.getExecutionId()), e.getLocalizedMessage()))
                    .build();

        }
    }

    @POST
    @Path(ShephardConstants.Resources.RESTART)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response restartWorkflow(@NotNull WorkflowManagementRequest restartWorkflowRequest) {

        try {
            log.info(String.format("Processing Restart Execution Request for %s", restartWorkflowRequest));

            Map<String, Object> restartedExecutionResponse = workflowManagementService.restartWorkflow(restartWorkflowRequest);

            log.info(String.format("Execution restarted successfully for %s", restartWorkflowRequest));

            return Response.ok(resourceHelper.createShepherdResponse(
                    ResourceName.RESTART_EXECUTION, restartedExecutionResponse,
                    "Execution restarted successfully.", null))
                    .build();

        } catch (Throwable e) {

            log.error(String.format("Restart Execution failed for client : %s, endpoint : %s, and executionId : %s",
                    restartWorkflowRequest.getClientName(), restartWorkflowRequest.getEndpointName(),
                    restartWorkflowRequest.getExecutionId()),
                    e);
            return Response.ok(resourceHelper.createShepherdResponse(
                    ResourceName.RESTART_EXECUTION, null,
                    String.format("Restart Execution failed for client : %s, endpoint : %s, and executionId : %s",
                            restartWorkflowRequest.getClientName(), restartWorkflowRequest.getEndpointName(),
                            restartWorkflowRequest.getExecutionId()), e.getLocalizedMessage()))
                    .build();

        }
    }
}
