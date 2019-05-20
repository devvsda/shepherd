package com.devsda.platform.shepherdcore.loader;

import com.devsda.platform.shepherd.model.GraphConfiguration;
import org.junit.Test;

import java.io.IOException;

public class JSONLoaderTest {

    @Test
    public void loadGraphConfigurationTest() throws IOException {

        String filePath = "src/test/resources/workflow_configuration.json";

        GraphConfiguration graphConfiguration = JSONLoader.load(filePath, GraphConfiguration.class);

        System.out.println(graphConfiguration);
    }

    @Test
    public void loadGraphConfigurationFromResourcesTest() throws IOException {

        String fileName = "workflow_configuration.json";

        GraphConfiguration graphConfiguration = JSONLoader.loadFromResources(fileName, GraphConfiguration.class);

        System.out.println(graphConfiguration);
    }
}
