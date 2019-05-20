package com.devsda.platform.shepherdcore.consumer;

import com.devsda.platform.shepherdcore.application.ShepherdApplication;
import com.devsda.platform.shepherdcore.model.ShepherdConfiguration;
import com.devsda.platform.shepherd.model.Node;
import com.google.inject.Injector;
import io.dropwizard.cli.ConfiguredCommand;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import net.sourceforge.argparse4j.inf.Namespace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NodeExecutorConsumer extends ConfiguredCommand<ShepherdConfiguration> {

    private static final Logger log = LoggerFactory.getLogger(NodeExecutorConsumer.class);

    @Override
    protected void run(Bootstrap<ShepherdConfiguration> bootstrap, Namespace namespace, ShepherdConfiguration shephardConfiguration) throws Exception {
        Environment environment = new Environment(bootstrap.getApplication().getName(),
                bootstrap.getObjectMapper(), bootstrap.getValidatorFactory().getValidator(),
                bootstrap.getMetricRegistry(), bootstrap.getClassLoader());
        shephardConfiguration.getMetricsFactory().configure(environment.lifecycle(),
                bootstrap.getMetricRegistry());
        bootstrap.run(shephardConfiguration, environment);
        
        Injector injector =  new ShepherdApplication().createInjector(shephardConfiguration, environment);


        // Execute Cron to poll RabbitMQ.
        while(true) {

            try {

                // TODO : Poll x messages from Queue.
                List<Node> readyToExecuteNodes = null;

                if(readyToExecuteNodes == null || readyToExecuteNodes.isEmpty()) {
                    continue;
                }

                ExecutorService executorService = Executors.newFixedThreadPool(10);

                for(Node node : readyToExecuteNodes) {
                    // executorService.submit(new NodeExecutor());
                }



            } catch (Exception e) {
                log.error("NodeExecutorConsumer failed.", e);
            }
        }




        //
    }

    public NodeExecutorConsumer() {
        super("NodeExecutorConsumer", "Helps execute ready to execute node-messges from primary queue.");
    }
}
