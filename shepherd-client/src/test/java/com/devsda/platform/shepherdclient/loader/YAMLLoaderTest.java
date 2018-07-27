package com.devsda.platform.shepherdclient.loader;

import com.devsda.platform.shepherdclient.model.ShepherdServerConfiguration;
import org.junit.Test;

public class YAMLLoaderTest {

    @Test
    public void loadServerConfigurationTest() throws Exception {

        String configurationFilePath = "./configurations/dev-shepherd-configuration.yaml";

        ShepherdServerConfiguration shepherdServerConfiguration = YAMLLoader.load(configurationFilePath, ShepherdServerConfiguration.class);

        System.out.println(shepherdServerConfiguration);
    }
}
