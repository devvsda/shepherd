package com.devsda.platform.shephardcore.resources;

import com.devsda.platform.shephardcore.constants.ShephardConstants;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path(ShephardConstants.Resources.HealthCheck)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HealthCheckReources {

    @Path("/{echoText}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response healthCheck(@PathParam("echoText") String echoText) {
        return Response.ok(echoText).build();
    }
}
