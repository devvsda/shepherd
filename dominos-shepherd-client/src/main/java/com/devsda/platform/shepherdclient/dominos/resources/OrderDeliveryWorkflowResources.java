package com.devsda.platform.shepherdclient.dominos.resources;

import com.devsda.platform.shepherd.model.ExecutionData;
import com.devsda.platform.shepherd.model.ShepherdExecutionResponse;
import com.devsda.platform.shepherdclient.dominos.constants.DominosShepherdClientConstants;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
    public Response validateOrder(ExecutionData doc) throws Exception {

        log.info(String.format("Validating order."));
        Map<String,Object> mp  = doc.getInitialPayload();

        mp.put("validateOrderUpdate", "vaidate order added this value");

        ExecutionData data =new ExecutionData(mp);

        // TODO : Process responsibility of this API
        Thread.sleep(500);

        return Response.ok(new ShepherdExecutionResponse("YES",data)).build();
    }

    @POST
    @Path(DominosShepherdClientConstants.Resources.GET_ORDER_TYPE)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getOrderType(ExecutionData doc) throws Exception {

        log.info(String.format("Validating order."));

        Map<String,Object> mp  = doc.getInitialPayload();

        mp.put("GetOrderType", "Get Order Type Updated this value");

        ExecutionData data =new ExecutionData(mp);


        // TODO : Process responsibility of this API
         Thread.sleep(500);


        return Response.ok(new ShepherdExecutionResponse("VEG",data)).build();
    }

    @POST
    @Path(DominosShepherdClientConstants.Resources.ASSIGN_NON_VEG_KITCHEN)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response assignNonVegKitchen(ExecutionData doc) throws Exception {

        log.info(String.format("Validating order."));
        Map<String,Object> mp  = doc.getInitialPayload();

        mp.put("AssignNnVegKitchen", "AssignNnVegKitchen Updated this value");

        ExecutionData data =new ExecutionData(mp);


        // TODO : Process responsibility of this API
        Thread.sleep(500);

        return Response.ok(new ShepherdExecutionResponse("Yes",data)).build();
    }

    @POST
    @Path(DominosShepherdClientConstants.Resources.ASSIGN_VEG_KITCHEN)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response assignVegKitchen(ExecutionData doc) throws Exception {

        log.info(String.format("Validating order."));

        Map<String,Object> mp  = doc.getInitialPayload();

        mp.put("assignVegKitchen", "assignVegKitchen Updated this value");

        ExecutionData data =new ExecutionData(mp);

        // TODO : Process responsibility of this API
        Thread.sleep(500);

        return Response.ok(new ShepherdExecutionResponse("Yes",data)).build();
    }

    @POST
    @Path(DominosShepherdClientConstants.Resources.ASSIGN_CHEF)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response assignChef(ExecutionData doc) throws Exception {

        log.info(String.format("Validating order."));

        Map<String,Object> mp  = doc.getInitialPayload();

        mp.put("assignChef", "assignChef Updated this value");

        ExecutionData data =new ExecutionData(mp);

        // TODO : Process responsibility of this API
        Thread.sleep(500);

        return Response.ok(new ShepherdExecutionResponse("Yes",data)).build();
    }

    @POST
    @Path(DominosShepherdClientConstants.Resources.GATHER_INGREDIENTS)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response gatherIngredients(ExecutionData doc) throws Exception {

        log.info(String.format("Validating order."));

        Map<String,Object> mp  = doc.getInitialPayload();

        mp.put("gatherIngredients", "gatherIngredients Updated this value");

        ExecutionData data =new ExecutionData(mp);

        // TODO : Process responsibility of this API
        Thread.sleep(500);

        return Response.ok(new ShepherdExecutionResponse("Yes",data)).build();
    }

    @POST
    @Path(DominosShepherdClientConstants.Resources.COOK_FOOD)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response cookFood(ExecutionData doc) throws Exception {

        log.info(String.format("Validating order."));

        Map<String,Object> mp  = doc.getInitialPayload();

        mp.put("cookFood", "cookFood Updated this value");

        ExecutionData data =new ExecutionData(mp);

        // TODO : Process responsibility of this API
        Thread.sleep(500);

        // TODO : Put data to documentDB, which will be used by subsequent nodes.

        return Response.ok(new ShepherdExecutionResponse("Yes",data)).build();
    }

    @POST
    @Path(DominosShepherdClientConstants.Resources.NOTIFY_CLIENT)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response notifyClient(ExecutionData doc) throws Exception {

        log.info(String.format("Validating order."));

        Map<String,Object> mp  = doc.getInitialPayload();

        mp.put("cookFood", "cookFood Updated this value");

        ExecutionData data =new ExecutionData(mp);

        // TODO : Process responsibility of this API
        Thread.sleep(500);

        return Response.ok(new ShepherdExecutionResponse("COMPLETED",data)).build();
    }
}
