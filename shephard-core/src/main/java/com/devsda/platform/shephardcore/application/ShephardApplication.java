package com.devsda.platform.shephardcore.application;

import com.devsda.platform.shephardcore.dao.RegisterationDao;
import com.devsda.platform.shephardcore.model.ShephardConfiguration;
import com.devsda.platform.shephardcore.resources.ClientDataRetrievalResources;
import com.devsda.platform.shephardcore.resources.ClientRegisterationResources;
import com.devsda.platform.shephardcore.resources.HealthCheckReources;
import com.devsda.platform.shephardcore.service.ClientDataRetrievelService;
import com.google.inject.*;
import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.skife.jdbi.v2.DBI;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

public class ShephardApplication extends Application<ShephardConfiguration> {

    public static void main(String[] args) throws Exception {
        new ShephardApplication().run(args);
    }

    public void run(ShephardConfiguration shephardConfiguration, Environment environment) throws Exception {

        Injector injector = createInjector(shephardConfiguration, environment);

        final FilterRegistration.Dynamic cors =
                environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");


        environment.jersey().register(HealthCheckReources.class);
        environment.jersey().register(injector.getInstance(ClientRegisterationResources.class));
        environment.jersey().register(injector.getInstance(ClientDataRetrievalResources.class));
    }

    public Injector createInjector(ShephardConfiguration shephardConfiguration, Environment environment) {

        Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {

                // Binding DB layer with Application layer.
                final DBIFactory factory = new DBIFactory();
                final DBI jdbi = factory.build(environment, shephardConfiguration.getDatabase(), "mysql");
                final RegisterationDao registerationDao = jdbi.onDemand(RegisterationDao.class);

                bind(RegisterationDao.class).toInstance(registerationDao);

                // Other objects
                bind(ShephardConfiguration.class).toInstance(shephardConfiguration);


            }
        });

        return injector;
    }
}
