package com.devsda.platform.shepherdcore.loader;

import com.devsda.platform.shepherdcore.model.ShephardConfiguration;
import org.junit.Test;

import java.io.IOException;

public class YamlLoaderTest {

    @Test
    public void loadTest() throws IOException {

        ShephardConfiguration shephardConfiguration = YamlLoader.load("scripts/dev-shepherd-configuration.yaml", ShephardConfiguration.class);

        System.out.println(shephardConfiguration);

    }
}
