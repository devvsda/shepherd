package com.devsda.platform.shephardcore;

import com.codahale.metrics.MetricRegistry;
import com.devsda.platform.shepherdcore.application.ShephardApplication;
import com.devsda.platform.shepherdcore.loader.YamlLoader;
import com.devsda.platform.shepherdcore.model.ShephardConfiguration;
import com.google.inject.Injector;
import io.dropwizard.setup.Environment;

import java.io.IOException;

public class ApplicationContextUtil {

    public static Injector createApplicationInjector() throws IOException {
        ShephardApplication shephardApplication = new ShephardApplication();

        ShephardConfiguration shephardConfiguration = YamlLoader.load("scripts/dev-shepherd-configuration.yaml", ShephardConfiguration.class);
        Environment environment = new Environment("ShephardCore", null, null, new MetricRegistry(), null);

        return shephardApplication.createInjector(shephardConfiguration, environment);
    }
}
