package com.devsda.platform.shepherdcore.resources;

import com.devsda.platform.shepherdcore.constants.ShephardConstants;
import com.devsda.platform.shepherdcore.service.documentservice.ExecutionDocumentService;
import com.devsda.platform.shepherd.model.ExecutionData;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

@Path(ShephardConstants.Resources.EXECUTION_DETAILS)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ExecutionDocumentResources {
    private static final Logger log = LoggerFactory.getLogger(HealthCheckReources.class);

    @Inject
    private ExecutionDocumentService executeWorkflowService;

    @Path("/{objectId}/{executionId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response fetchExecutionDetails(@PathParam("objectId") String objectId, @PathParam("executionId") String executionID) throws Exception {

        try {
            Document result = this.executeWorkflowService.fetchExecutionDetails(objectId, executionID);
            if (result == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("No Document found for " + executionID).build();
            }

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

            String strigyfiedObject = mapper.writeValueAsString(result);
            return Response.ok(strigyfiedObject).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Something went wrong " + e.getMessage()).build();
        }
    }

    @Path("/{executionId}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateExecutionDetails(@PathParam("objectId") String objectId, @PathParam("executionId") String executionID, @NotNull Map<String, Object> updatedExecutionDetails) throws Exception {

        ExecutionData updatedExecutionData = new ExecutionData(updatedExecutionDetails);
        try {
            boolean result = this.executeWorkflowService.updateExecutionDetails(objectId, executionID, updatedExecutionData);
            if (result == false) {
                return Response.status(Response.Status.NOT_MODIFIED).entity("Could not update the document with executionId: " + executionID + "update :" + updatedExecutionDetails).build();
            }

            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Something went wrong " + e.getMessage()).build();
        }
    }


}
