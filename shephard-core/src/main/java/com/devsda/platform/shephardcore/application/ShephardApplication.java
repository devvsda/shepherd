package com.devsda.platform.shephardcore.application;

import com.devsda.platform.shephardcore.model.ShephardConfiguration;
import com.devsda.platform.shephardcore.resources.HealthCheckReources;
import com.google.inject.*;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class ShephardApplication extends Application<ShephardConfiguration> {

    public static void main(String[] args) throws Exception {
        new ShephardApplication().run(args);
    }

    public void run(ShephardConfiguration shephardConfiguration, Environment environment) throws Exception {

        Injector injector = createInjector();

        environment.jersey().register(HealthCheckReources.class);
    }

    private Injector createInjector() {

        Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(ShephardConfiguration.class).toInstance(new ShephardConfiguration());
            }
        });

        return injector;
    }
}
