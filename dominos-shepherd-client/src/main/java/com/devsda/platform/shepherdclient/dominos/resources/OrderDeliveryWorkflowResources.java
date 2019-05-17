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
    public Response validateOrder(String doc) throws Exception {

        log.info(String.format("Validating order."));
        Map<String,Object> mp  = doc.getInitialPayload();

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String,Object>> typeRef
                = new TypeReference<HashMap<String,Object>>() {};

        HashMap<String,Object> mp = mapper.readValue(doc, typeRef);

        mp.put("validateOrderUpdate", "vaidate order added this value");

        // TODO : Process responsibility of this API
        Thread.sleep(20000);

        return Response.ok(new ShepherdExecutionResponse("YES", JSONLoader.stringify(mp))).build();
    }

    @POST
    @Path(DominosShepherdClientConstants.Resources.GET_ORDER_TYPE)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getOrderType(String doc) throws Exception {

        log.info(String.format("Validating order."));

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String,Object>> typeRef
                = new TypeReference<HashMap<String,Object>>() {};

        HashMap<String,Object> mp = mapper.readValue(doc, typeRef);

        mp.put("GetOrderType", "Get Order Type Updated this value");

        // TODO : Process responsibility of this API
         Thread.sleep(20000);


        return Response.ok(new ShepherdExecutionResponse("VEG",JSONLoader.stringify(mp))).build();
    }

    @POST
    @Path(DominosShepherdClientConstants.Resources.ASSIGN_NON_VEG_KITCHEN)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response assignNonVegKitchen(String doc) throws Exception {

        log.info(String.format("Validating order."));
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String,Object>> typeRef
                = new TypeReference<HashMap<String,Object>>() {};

        HashMap<String,Object> mp = mapper.readValue(doc, typeRef);

        mp.put("AssignNnVegKitchen", "AssignNnVegKitchen Updated this value");

        // TODO : Process responsibility of this API
        Thread.sleep(20000);

        return Response.ok(new ShepherdExecutionResponse("Yes",JSONLoader.stringify(mp))).build();
    }

    @POST
    @Path(DominosShepherdClientConstants.Resources.ASSIGN_VEG_KITCHEN)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response assignVegKitchen(String doc) throws Exception {

        log.info(String.format("Validating order."));

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String,Object>> typeRef
                = new TypeReference<HashMap<String,Object>>() {};

        HashMap<String,Object> mp = mapper.readValue(doc, typeRef);

        mp.put("assignVegKitchen", "assignVegKitchen Updated this value");

        // TODO : Process responsibility of this API
        Thread.sleep(20000);

        return Response.ok(new ShepherdExecutionResponse("Yes",JSONLoader.stringify(mp))).build();
    }

    @POST
    @Path(DominosShepherdClientConstants.Resources.ASSIGN_CHEF)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response assignChef(String doc) throws Exception {

        log.info(String.format("Validating order."));

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String,Object>> typeRef
                = new TypeReference<HashMap<String,Object>>() {};

        HashMap<String,Object> mp = mapper.readValue(doc, typeRef);

        mp.put("assignChef", "assignChef Updated this value");

        // TODO : Process responsibility of this API
        Thread.sleep(20000);

        return Response.ok(new ShepherdExecutionResponse("Yes",JSONLoader.stringify(mp))).build();
    }

    @POST
    @Path(DominosShepherdClientConstants.Resources.GATHER_INGREDIENTS)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response gatherIngredients(String doc) throws Exception {

        log.info(String.format("Validating order."));

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String,Object>> typeRef
                = new TypeReference<HashMap<String,Object>>() {};

        HashMap<String,Object> mp = mapper.readValue(doc, typeRef);

        mp.put("gatherIngredients", "gatherIngredients Updated this value");

        // TODO : Process responsibility of this API
        Thread.sleep(20000);

        return Response.ok(new ShepherdExecutionResponse("Yes",JSONLoader.stringify(mp))).build();
    }

    @POST
    @Path(DominosShepherdClientConstants.Resources.COOK_FOOD)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response cookFood(String doc) throws Exception {

        log.info(String.format("Validating order."));

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String,Object>> typeRef
                = new TypeReference<HashMap<String,Object>>() {};

        HashMap<String,Object> mp = mapper.readValue(doc, typeRef);

        mp.put("cookFood", "cookFood Updated this value");

        // TODO : Process responsibility of this API
        Thread.sleep(20000);

        // TODO : Put data to documentDB, which will be used by subsequent nodes.

        return Response.ok(new ShepherdExecutionResponse("Yes",JSONLoader.stringify(mp))).build();
    }

    @POST
    @Path(DominosShepherdClientConstants.Resources.NOTIFY_CLIENT)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response notifyClient(String doc) throws Exception {

        log.info(String.format("Validating order."));

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String,Object>> typeRef
                = new TypeReference<HashMap<String,Object>>() {};

        HashMap<String,Object> mp = mapper.readValue(doc, typeRef);

        mp.put("cookFood", "cookFood Updated this value");

        // TODO : Process responsibility of this API
        Thread.sleep(20000);

        return Response.ok(new ShepherdExecutionResponse("COMPLETED",JSONLoader.stringify(mp))).build();
    }
}
