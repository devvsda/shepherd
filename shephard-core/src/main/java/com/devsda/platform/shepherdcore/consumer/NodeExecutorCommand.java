package com.devsda.platform.shepherdcore.consumer;

import com.devsda.platform.shepherdcore.application.ShepherdApplication;
import com.devsda.platform.shepherdcore.model.ShepherdConfiguration;
import com.devsda.platform.shepherd.model.Node;
import com.google.inject.Injector;
import io.dropwizard.Application;
import io.dropwizard.cli.ConfiguredCommand;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import net.sourceforge.argparse4j.inf.Namespace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NodeExecutorCommand extends ConfiguredCommand<ShepherdConfiguration> {

    private static final Logger log = LoggerFactory.getLogger(NodeExecutorCommand.class);

    public static void main(String[] args) {

    }

    @Override
    protected void run(Bootstrap<ShepherdConfiguration> bootstrap, Namespace namespace, ShepherdConfiguration shephardConfiguration) throws Exception {

        Environment environment = new Environment(bootstrap.getApplication().getName(),
                bootstrap.getObjectMapper(), bootstrap.getValidatorFactory().getValidator(),
                bootstrap.getMetricRegistry(), bootstrap.getClassLoader());
        shephardConfiguration.getMetricsFactory().configure(environment.lifecycle(),
                bootstrap.getMetricRegistry());
        bootstrap.run(shephardConfiguration, environment);

        Injector injector =  new ShepherdApplication().createInjector(shephardConfiguration, environment);

        NodeExecutorConsumer nodeExecutorConsumer = injector.getInstance(NodeExecutorConsumer.class);

        nodeExecutorConsumer.consume();


    }

    public NodeExecutorCommand() {
        super("NodeExecutorCommand", "Helps execute ready to execute node-messges from primary queue.");
    }
}
