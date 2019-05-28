package com.devsda.platform.shepherdcore;

import com.codahale.metrics.MetricRegistry;
import com.devsda.platform.shepherdcore.application.ShepherdApplication;
import com.devsda.platform.shepherdcore.loader.YamlLoader;
import com.devsda.platform.shepherdcore.model.ShepherdConfiguration;
import com.google.inject.Injector;
import io.dropwizard.setup.Environment;

import java.io.IOException;

public class ApplicationContextUtil {

    public static Injector createApplicationInjector() throws IOException {
        ShepherdApplication shepherdApplication = new ShepherdApplication();

        ShepherdConfiguration shepherdConfiguration = YamlLoader.load("scripts/dev-shepherd-configuration.yaml", ShepherdConfiguration.class);
        Environment environment = new Environment("ShephardCore", null, null, new MetricRegistry(), null);

        return shepherdApplication.createInjector(shepherdConfiguration, environment);
    }
}
