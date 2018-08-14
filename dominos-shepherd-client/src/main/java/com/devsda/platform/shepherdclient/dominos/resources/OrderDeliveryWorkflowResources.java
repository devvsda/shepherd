package com.devsda.platform.shepherdclient.dominos.resources;

import com.devsda.platform.shepherdclient.dominos.constants.DominosShepherdClientConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

@Path(DominosShepherdClientConstants.Resources.ORDER_MANAGEMENT_WORKFLOW)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderDeliveryWorkflowResources {

    private static final Logger log = LoggerFactory.getLogger(OrderDeliveryWorkflowResources.class);

    @POST
    @Path(DominosShepherdClientConstants.Resources.VALIDATE_ORDER)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response validateOrder() throws Exception {

        log.info(String.format("Validating order."));

        // TODO : Fetch processed data from DocumentDB.

        // TODO : Process responsibility of this API

        // TODO : Put data to documentDB, which will be used by subsequent nodes.

        return Response.ok("VALIDATE_ORDER").build();
    }

    @POST
    @Path(DominosShepherdClientConstants.Resources.GET_ORDER_TYPE)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getOrderType() throws Exception {

        log.info(String.format("Validating order."));

        // TODO : Fetch processed data from DocumentDB.

        // TODO : Process responsibility of this API

        // TODO : Put data to documentDB, which will be used by subsequent nodes.

        return Response.ok("GET_ORDER_TYPE").build();
    }

    @POST
    @Path(DominosShepherdClientConstants.Resources.ASSIGN_NON_VEG_KITCHEN)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response assignNonVegKitchen() throws Exception {

        log.info(String.format("Validating order."));

        // TODO : Fetch processed data from DocumentDB.

        // TODO : Process responsibility of this API

        // TODO : Put data to documentDB, which will be used by subsequent nodes.

        return Response.ok("ASSIGN_NON_VEG_KITCHEN").build();
    }

    @POST
    @Path(DominosShepherdClientConstants.Resources.ASSIGN_VEG_KITCHEN)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response assignVegKitchen() throws Exception {

        log.info(String.format("Validating order."));

        // TODO : Fetch processed data from DocumentDB.

        // TODO : Process responsibility of this API

        // TODO : Put data to documentDB, which will be used by subsequent nodes.

        return Response.ok("ASSIGN_VEG_KITCHEN").build();
    }

    @POST
    @Path(DominosShepherdClientConstants.Resources.ASSIGN_CHEF)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response assignChef() throws Exception {

        log.info(String.format("Validating order."));

        // TODO : Fetch processed data from DocumentDB.

        // TODO : Process responsibility of this API

        // TODO : Put data to documentDB, which will be used by subsequent nodes.

        return Response.ok("ASSIGN_CHEF").build();
    }

    @POST
    @Path(DominosShepherdClientConstants.Resources.GATHER_INGREDIENTS)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response gatherIngredients() throws Exception {

        log.info(String.format("Validating order."));

        // TODO : Fetch processed data from DocumentDB.

        // TODO : Process responsibility of this API

        // TODO : Put data to documentDB, which will be used by subsequent nodes.

        return Response.ok("GATHER_INGREDIENTS").build();
    }

    @POST
    @Path(DominosShepherdClientConstants.Resources.COOK_FOOD)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response cookFood() throws Exception {

        log.info(String.format("Validating order."));

        // TODO : Fetch processed data from DocumentDB.

        // TODO : Process responsibility of this API

        // TODO : Put data to documentDB, which will be used by subsequent nodes.

        return Response.ok("COOK_FOOD").build();
    }

    @POST
    @Path(DominosShepherdClientConstants.Resources.NOTIFY_CLIENT)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response notifyClient() throws Exception {

        log.info(String.format("Validating order."));

        // TODO : Fetch processed data from DocumentDB.

        // TODO : Process responsibility of this API

        // TODO : Put data to documentDB, which will be used by subsequent nodes.

        return Response.ok("NOTIFY_CLIENT").build();
    }
}