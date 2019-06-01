package com.devsda.platform.shepherdclient.dominos.resources;

import com.devsda.platform.shepherd.model.ShepherdExecutionResponse;
import com.devsda.platform.shepherdclient.dominos.constants.DominosShepherdClientConstants;
import com.devsda.platform.shepherdclient.loader.JSONLoader;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;

@Path(DominosShepherdClientConstants.Resources.ORDER_MANAGEMENT_WORKFLOW)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderDeliveryWorkflowResources {

    private static final Logger log = LoggerFactory.getLogger(OrderDeliveryWorkflowResources.class);

    @POST
    @Path(DominosShepherdClientConstants.Resources.VALIDATE_ORDER)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response validateOrder(String doc) throws Exception {

        log.info(String.format("Executing ValidateOrderUpdate"));

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String,Object>> typeRef
                = new TypeReference<HashMap<String,Object>>() {};

        HashMap<String,Object> mp = mapper.readValue(doc, typeRef);

        mp.put("ValidateOrderUpdate", "Validate order added this value");

        Thread.sleep(2000);

        return Response.ok(new ShepherdExecutionResponse("YES", JSONLoader.stringify(mp))).build();
    }

    @POST
    @Path(DominosShepherdClientConstants.Resources.GET_ORDER_TYPE)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getOrderType(String doc) throws Exception {

        log.info(String.format("Executing GetOrderType"));

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String,Object>> typeRef
                = new TypeReference<HashMap<String,Object>>() {};

        HashMap<String,Object> mp = mapper.readValue(doc, typeRef);

        mp.put("GetOrderType", "Get Order Type Updated this value");

         Thread.sleep(2000);


        return Response.ok(new ShepherdExecutionResponse("VEG",JSONLoader.stringify(mp))).build();
    }

    @POST
    @Path(DominosShepherdClientConstants.Resources.ASSIGN_NON_VEG_KITCHEN)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response assignNonVegKitchen(String doc) throws Exception {

        log.info(String.format("Executing AssignNnVegKitchen"));
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String,Object>> typeRef
                = new TypeReference<HashMap<String,Object>>() {};

        HashMap<String,Object> mp = mapper.readValue(doc, typeRef);

        mp.put("AssignNnVegKitchen", "AssignNnVegKitchen Updated this value");

        Thread.sleep(2000);

        return Response.ok(new ShepherdExecutionResponse("Yes",JSONLoader.stringify(mp))).build();
    }

    @POST
    @Path(DominosShepherdClientConstants.Resources.ASSIGN_VEG_KITCHEN)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response assignVegKitchen(String doc) throws Exception {

        log.info(String.format("Executing AssignVegKitchen"));

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String,Object>> typeRef
                = new TypeReference<HashMap<String,Object>>() {};

        HashMap<String,Object> mp = mapper.readValue(doc, typeRef);

        mp.put("AssignVegKitchen", "assignVegKitchen Updated this value");

        Thread.sleep(2000);

        return Response.ok(new ShepherdExecutionResponse("Yes",JSONLoader.stringify(mp))).build();
    }

    @POST
    @Path(DominosShepherdClientConstants.Resources.ASSIGN_CHEF)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response assignChef(String doc) throws Exception {

        log.info(String.format("Executing AssignChef"));

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String,Object>> typeRef
                = new TypeReference<HashMap<String,Object>>() {};

        HashMap<String,Object> mp = mapper.readValue(doc, typeRef);

        mp.put("AssignChef", "assignChef Updated this value");

        Thread.sleep(2000);

        return Response.ok(new ShepherdExecutionResponse("Yes",JSONLoader.stringify(mp))).build();
    }

    @POST
    @Path(DominosShepherdClientConstants.Resources.GATHER_INGREDIENTS)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response gatherIngredients(String doc) throws Exception {

        log.info(String.format("Executing GatherIngredients"));

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String,Object>> typeRef
                = new TypeReference<HashMap<String,Object>>() {};

        HashMap<String,Object> mp = mapper.readValue(doc, typeRef);

        mp.put("GatherIngredients", "gatherIngredients Updated this value");

        Thread.sleep(2000);

        return Response.ok(new ShepherdExecutionResponse("Yes",JSONLoader.stringify(mp))).build();
    }

    @POST
    @Path(DominosShepherdClientConstants.Resources.COOK_FOOD)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response cookFood(String doc) throws Exception {

        log.info(String.format("Executing CookFood"));

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String,Object>> typeRef
                = new TypeReference<HashMap<String,Object>>() {};

        HashMap<String,Object> mp = mapper.readValue(doc, typeRef);

        mp.put("CookFood", "cookFood Updated this value");

        Thread.sleep(2000);

        return Response.ok(new ShepherdExecutionResponse("Yes",JSONLoader.stringify(mp))).build();
    }

    @POST
    @Path(DominosShepherdClientConstants.Resources.NOTIFY_CLIENT)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response notifyClient(String doc) throws Exception {

        log.info(String.format("Executing NotifyClient"));

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String,Object>> typeRef
                = new TypeReference<HashMap<String,Object>>() {};

        HashMap<String,Object> mp = mapper.readValue(doc, typeRef);

        mp.put("NotifyClient", "cookFood Updated this value");

        Thread.sleep(2000);

        return Response.ok(new ShepherdExecutionResponse("COMPLETED",JSONLoader.stringify(mp))).build();
    }
}
