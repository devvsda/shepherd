package com.devsda.platform.shephardcore.loader;

import com.devsda.platform.shephardcore.model.GraphConfiguration;
import com.devsda.platform.shephardcore.model.ShephardConfiguration;
import com.devsda.platform.shephardcore.model.TeamConfiguration;
import org.junit.Test;

import java.io.IOException;

public class YamlLoaderTest {

    @Test
    public void loadTest() throws IOException {

        ShephardConfiguration shephardConfiguration = YamlLoader.load("scripts/dev-shepherd-configuration.yaml", ShephardConfiguration.class);

        System.out.println(shephardConfiguration);

    }
}
