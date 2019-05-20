package com.devsda.platform.shepherdcore.resources;

import com.devsda.platform.shepherdcore.constants.ShephardConstants;
import com.devsda.platform.shepherdcore.model.HealthCheck;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

@Path(ShephardConstants.Resources.HealthCheck)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HealthCheckReources {
    // TODO: apply proper indentation.
    private static final Logger log = LoggerFactory.getLogger(HealthCheckReources.class);

    @Path("/{echoText}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response healthCheck(@PathParam("echoText") String echoText) throws Exception {

        Map<String, String> response = new HashMap<>();

        response.put("echoString", echoText);
        response.put("hostName", InetAddress.getLocalHost().getHostName());

        ObjectMapper objectMapper = new ObjectMapper();
        String stringifyResponse = objectMapper.writeValueAsString(response);

        return Response.ok(stringifyResponse).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response healthCheckWithParameters(@QueryParam("echoText") String echoText) throws Exception {

        Map<String, String> response = new HashMap<>();

        response.put("echoString", echoText);
        response.put("hostName", InetAddress.getLocalHost().getHostName());

        ObjectMapper objectMapper = new ObjectMapper();

        String stringifyResponse = objectMapper.writeValueAsString(response);
        return Response.ok(stringifyResponse).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response healthCheck(@NotNull HealthCheck healthCheck) throws Exception {

        Map<String, String> response = new HashMap<>();

        response.put("echoString", healthCheck.getEchoText());
        response.put("hostName", InetAddress.getLocalHost().getHostName());

        ObjectMapper objectMapper = new ObjectMapper();
        String stringifyResponse = objectMapper.writeValueAsString(response);

        return Response.ok(stringifyResponse).build();
    }
}