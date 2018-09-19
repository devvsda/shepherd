package com.devsda.platform.shepherdclient.impl;

import com.devsda.platform.shepherd.model.ShepherdResponse;
import com.devsda.platform.shepherdclient.constants.Environment;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

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

        ShepherdResponse shepherdResponse = shepherdClient.registerClient("hitesh_dev_1");
        System.out.println(shepherdResponse);

    }
    

    @Test
    public void retrieveEndpointTest() {

        String clientName = "hitesh_dev_1";
        String endpointName = "sdk_testing";

        ShepherdResponse shepherdResponse = shepherdClient.retrieveEndpoint(clientName, endpointName);

        System.out.println(shepherdResponse);

    }

    @Test
    public void registerEndpointTest() {

        String graphFilePath = "./src/test/resources/sample_workflow.xml";
        String endpointFilePath = "./src/test/resources/workflow_configuration.json";
        ShepherdResponse registerClientResponse = shepherdClient.registerClient("hitesh_dev");
        System.out.println(registerClientResponse);

        ShepherdResponse registerEndpointResponse = shepherdClient.registerEndpoint("hitesh_dev", "sdk_testing", graphFilePath, endpointFilePath);
        System.out.println(registerEndpointResponse);
    }
}
