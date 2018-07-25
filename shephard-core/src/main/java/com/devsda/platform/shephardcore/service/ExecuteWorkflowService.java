package com.devsda.platform.shephardcore.service;

import com.devsda.platform.shephardcore.graphgenerator.DAGGenerator;
import com.devsda.platform.shephardcore.loader.JSONLoader;
import com.devsda.platform.shephardcore.loader.YamlLoader;
import com.devsda.platform.shephardcore.model.Graph;
import com.devsda.platform.shephardcore.model.GraphConfiguration;

import java.util.Map;

public class ExecuteWorkflowService {


    public void executeWorkflow(String clientId, String endpointId, Map<String, Object> initialPayload) throws Exception {


        // TODO : Fetch required details from Database layer.
        // TODO : For now, I am using sample workflow , and its configurations.


        // Generate graph details.
        DAGGenerator dagGenerator = new DAGGenerator();
        String workflowFilePath = "./src/test/resources/sample_workflow.xml";
        Graph graph = dagGenerator.generate(workflowFilePath);

        // Load graph configurations.
        String workflowConfigurationPath = "./src/test/resources/workflow_configuration.json";
        GraphConfiguration graphConfiguration = JSONLoader.load(workflowConfigurationPath, GraphConfiguration.class);

        return;
    }
}
