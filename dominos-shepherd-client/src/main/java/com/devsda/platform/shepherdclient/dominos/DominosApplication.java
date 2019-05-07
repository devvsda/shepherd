package com.devsda.platform.shepherdclient.dominos;

import com.devsda.platform.shepherdclient.dominos.resources.OrderDeliveryWorkflowResources;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

public class DominosApplication extends Application<DominosConfiguration> {


    public static void main(String[] args) throws Exception {
        new DominosApplication().run(args);
    }

    @Override
    public void run(DominosConfiguration dominosConfiguration, Environment environment) throws Exception {

        final FilterRegistration.Dynamic cors =
                environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");


        environment.jersey().register(OrderDeliveryWorkflowResources.class);
    }
}
