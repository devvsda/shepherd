package com.devsda.platform.shepherdclient.impl;

import com.devsda.platform.shepherd.model.ShepherdResponse;
import com.devsda.platform.shepherdclient.constants.Environment;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ShepherdClientTest {

    private static ShepherdClient shepherdClient;

    @BeforeClass
    public static void setUp() throws Exception {
        shepherdClient = new ShepherdClient(Environment.DEV);
    }

    @AfterClass
    public static void tearDown() {

    }

    @Test
    public void registerClientTest() throws JsonProcessingException {

        ShepherdResponse shepherdResponse = shepherdClient.registerClient("dominos");
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(shepherdResponse));

    }

    @Test
    public void retrieveEndpointTest() {

        String clientName = "amazon";
        String endpointName = "prime";

        ShepherdResponse shepherdResponse = shepherdClient.retrieveEndpoint(clientName, endpointName);

        System.out.println(shepherdResponse);

    }

    @Test
    public void registerEndpointTest() throws Exception {

        String graphFilePath = "./src/test/resources/sample_workflow.xml";
        String endpointFilePath = "./src/test/resources/workflow_configuration.json";

        ShepherdResponse registerEndpointResponse = shepherdClient.registerEndpoint("dominos", "logistics_dev", graphFilePath, endpointFilePath);

        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(registerEndpointResponse));
    }


    @Test
    public void updateWorkflowDetailsTest() throws Exception {
        String graphFilePath = "./src/test/resources/sample_workflow.xml";

        ShepherdResponse registerEndpointResponse = shepherdClient.updateWorkflowDetails("dominos", "logistics_dev", graphFilePath);
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(registerEndpointResponse));
    }

    @Test
    public void updateEndpointDetailsTest() throws Exception {
        String endpointFilePath = "./src/test/resources/workflow_configuration.json";

        ShepherdResponse registerEndpointResponse = shepherdClient.updateEndpointDetails("dominos", "logistics_dev", endpointFilePath);
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(registerEndpointResponse));
    }

    @Test
    public void executeEndpointTest() throws Exception {

        String clientName = "dominos";
        String endpointName = "logistics_dev";

        Map<String, Object> initialPayload = new HashMap<String, Object>()
        {{
            put("size", "medium");
            put("base", "cheese_crust");
            put("name", "farm_hosue");
        }};

        ShepherdResponse executeEndpointResponse = shepherdClient.executeEndpoint(clientName, endpointName, initialPayload);
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(executeEndpointResponse));
    }
}
