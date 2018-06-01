package com.devsda.platform.shephardcore;

import com.devsda.platform.shephardcore.application.ShephardApplication;
import com.devsda.platform.shephardcore.model.ShephardConfiguration;
import com.devsda.platform.shephardcore.util.YamlLoader;
import com.google.inject.Injector;
import io.dropwizard.setup.Environment;

import java.io.IOException;

public class ApplicationSetupUtil {

    public static Injector setupApplication() throws IOException {
        ShephardApplication shephardApplication = new ShephardApplication();

        ShephardConfiguration shephardConfiguration = YamlLoader.load("scripts/dev-shephard-configuration.yaml", ShephardConfiguration.class);
        Environment environment = new Environment("ShephardCore", null, null, null, null);

        return shephardApplication.createInjector(shephardConfiguration, environment);
    }
}
