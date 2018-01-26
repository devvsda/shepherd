package com.devsda.platform.shephardcore.resources;

import com.devsda.platform.shephardcore.constants.ShephardConstants;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

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

    @Path("")
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
}