package com.devsda.platform.shepherdcore.loader;

import com.devsda.platform.shepherdcore.model.ShepherdConfiguration;
import org.junit.Test;

import java.io.IOException;

public class YamlLoaderTest {

    @Test
    public void loadTest() throws IOException {

        ShepherdConfiguration shepherdConfiguration = YamlLoader.load("scripts/dev-shepherd-configuration.yaml", ShepherdConfiguration.class);

        System.out.println(shepherdConfiguration);

    }
}
