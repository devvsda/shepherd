package com.devsda.platform.shepherdclient.impl;

import com.devsda.platform.shepherd.model.ShepherdResponse;
import com.devsda.platform.shepherdclient.constants.Environment;
import com.devsda.platform.shepherdclient.loader.JSONLoader;
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

        ShepherdResponse shepherdResponse = shepherdClient.registerClient("dominos11");
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(shepherdResponse));

    }

    @Test
    public void retrieveEndpointTest() {

        String clientName = "bcci";
        String endpointName = "selection_dev";

        ShepherdResponse shepherdResponse = shepherdClient.retrieveEndpoint(clientName, endpointName);

        System.out.println(shepherdResponse);

    }

    @Test
    public void registerEndpointTest() throws Exception {

        String graphFilePath = "./src/test/resources/sample_workflow.xml";
        String endpointFilePath = "./src/test/resources/workflow_configuration.json";

        ShepherdResponse registerEndpointResponse = shepherdClient.registerEndpoint("dominos11", "validate_dev", graphFilePath, endpointFilePath);

        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(registerEndpointResponse));
    }

    @Test
    public void updateEndpointDetailsTest() throws Exception {

        String workflowGraphPath = "./src/test/resources/sample_workflow.xml";
        String endpointFilePath = "./src/test/resources/workflow_configuration.json";

        ShepherdResponse registerEndpointResponse = shepherdClient.updateEndpointDetails("bcci", "selection_dev", workflowGraphPath, endpointFilePath);
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(registerEndpointResponse));
    }

    @Test
    public void executeEndpointTest() throws Exception {

        String clientName = "bcci";
        String endpointName = "selection_dev";

        Map<String, Object> initialPayload = new HashMap<String, Object>() {{
            put("size", "medium");
            put("base", "cheese_crust");
            put("name", "farm_hosue");
        }};

        ShepherdResponse executeEndpointResponse = shepherdClient.executeEndpoint(clientName, endpointName, JSONLoader.stringify(initialPayload) );
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(executeEndpointResponse));
    }

    @Test
    public void resumeExecutionTest() throws Exception {

        String clientName = "bcci";
        String endpointName = "selection_dev";

        String objectId = "2b2b98c98c7345a2ab8f55878afdf0e2";
        String executionId = "055cc19de4ff4f54b27338bf76be1ec3";

        ShepherdResponse resumeExecutionResponse = shepherdClient.resumeExecution(clientName, endpointName, objectId, executionId);
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(resumeExecutionResponse));
    }

    @Test
    public void killExecutionTest() throws Exception {
        String clientName = "swiggy";
        String endpointName = "order_management_dev";

        String objectId = "c9af4c7e0203445d80ed766229d53a8e";
        String executionId = "05fcf0aa0b2f4b24942b993cbfc2badb";

        ShepherdResponse resumeExecutionResponse = shepherdClient.killExecution(clientName, endpointName, objectId, executionId);
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(resumeExecutionResponse));
    }


    @Test
    public void executeAndKillTest() throws Exception {

        String clientName = "bcci";
        String endpointName = "selection_dev";

        Map<String, Object> initialPayload = new HashMap<String, Object>() {{
            put("size", "medium");
            put("base", "cheese_crust");
            put("name", "farm_hosue");
        }};

        ShepherdResponse executeEndpointResponse = shepherdClient.executeEndpoint(clientName, endpointName, JSONLoader.stringify(initialPayload) );

        Map<String, Object> stringObjectMap = executeEndpointResponse.getResponseData();

        String objectId = (String) stringObjectMap.get("objectId");
        String executionId = (String) stringObjectMap.get("executionId");

        System.out.println(objectId);
        System.out.println(executionId);

        ShepherdResponse resumeExecutionResponse = shepherdClient.killExecution(clientName, endpointName, objectId, executionId);
    }

    @Test
    public void restartExecutionTest() throws Exception {

        String clientName = "bcci";
        String endpointName = "selection_dev";

        String objectId = "bcc6e0f0dd2949bab5f333cd2895a906";
        String executionId = "6ff0602db00e4597a40b43ca17114ddf";

        ShepherdResponse resumeExecutionResponse = shepherdClient.restartExecution(clientName, endpointName, objectId, executionId);
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(resumeExecutionResponse));

    }

    @Test
    public void getExecutionState() {

        String clientName = "dominos11";
        String endpointName = "validate_dev";
        String objectId = "0b1bb945e6184a5ab80828e3ab71f0d0";
        String executionId = "5a40591a68b7461bba7bd3023c366a54";

        ShepherdResponse getExecutionStateResponse = shepherdClient.getExecutionState(clientName, endpointName, objectId, executionId);

        System.out.println(getExecutionStateResponse);
    }

    @Test
    public void fetchAllExecutions() {
        String clientName = "bcci";
        String endpointName = "selection_dev";

        ShepherdResponse shepherdResponse = shepherdClient.getAllExecutions(clientName, endpointName);

        System.out.println(shepherdResponse);
    }

}
