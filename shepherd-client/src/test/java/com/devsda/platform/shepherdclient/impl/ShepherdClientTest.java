package com.devsda.platform.shepherdclient.impl;

import com.devsda.platform.shepherd.model.ShepherdResponse;
import com.devsda.platform.shepherdclient.constants.Environment;
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
    public void registerClientTest() {

        ShepherdResponse shepherdResponse = shepherdClient.registerClient("amazon");
        System.out.println(shepherdResponse);

    }

    @Test
    public void retrieveEndpointTest() {

        String clientName = "amazon";
        String endpointName = "prime";

        ShepherdResponse shepherdResponse = shepherdClient.retrieveEndpoint(clientName, endpointName);

        System.out.println(shepherdResponse);

    }

    @Test
    public void registerEndpointTest() {

        String graphFilePath = "./src/test/resources/sample_workflow.xml";
        String endpointFilePath = "./src/test/resources/workflow_configuration.json";
        ShepherdResponse registerClientResponse = shepherdClient.registerClient("amazon");
        System.out.println(registerClientResponse);

        ShepherdResponse registerEndpointResponse = shepherdClient.registerEndpoint("amazon", "prime", graphFilePath, endpointFilePath);
        System.out.println(registerEndpointResponse);
    }


    @Test
    public void updateWorkflowDetailsTest() {
        String graphFilePath = "./src/test/resources/sample_workflow.xml";

        ShepherdResponse registerEndpointResponse = shepherdClient.updateWorkflowDetails("amazon", "prime", graphFilePath);
        System.out.println(registerEndpointResponse);
    }

    @Test
    public void updateEndpointDetailsTest() {
        String endpointFilePath = "./src/test/resources/workflow_configuration.json";

        ShepherdResponse registerEndpointResponse = shepherdClient.updateEndpointDetails("amazon", "prime", endpointFilePath);
        System.out.println(registerEndpointResponse);
    }

    @Test
    public void executeEndpointTest() {

        String clientName = "amazon";
        String endpointName = "prime";

        Map<String, Object> initialPayload = new HashMap<String, Object>()
        {{
            put("size", "medium");
            put("base", "cheese_crust");
            put("name", "farm_hosue");
        }};

        ShepherdResponse executeEndpointResponse = shepherdClient.executeEndpoint(clientName, endpointName, initialPayload);
        System.out.println(executeEndpointResponse);
    }
}
