package com.devsda.platform.shephardcore.consumer;

import com.devsda.platform.shephardcore.application.ShephardApplication;
import com.devsda.platform.shephardcore.model.ShephardConfiguration;
import com.google.inject.Injector;
import io.dropwizard.cli.ConfiguredCommand;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import net.sourceforge.argparse4j.inf.Namespace;

public class NodeConsumer extends ConfiguredCommand<ShephardConfiguration> {

    @Override
    protected void run(Bootstrap<ShephardConfiguration> bootstrap, Namespace namespace, ShephardConfiguration shephardConfiguration) throws Exception {
        Environment environment = new Environment(bootstrap.getApplication().getName(),
                bootstrap.getObjectMapper(), bootstrap.getValidatorFactory().getValidator(),
                bootstrap.getMetricRegistry(), bootstrap.getClassLoader());
        shephardConfiguration.getMetricsFactory().configure(environment.lifecycle(),
                bootstrap.getMetricRegistry());
        bootstrap.run(shephardConfiguration, environment);
        
        Injector injector =  new ShephardApplication().createInjector(shephardConfiguration, environment);

        SampleExecutor sampleExecutor = injector.getInstance(SampleExecutor.class);
        sampleExecutor.sample();
    }

    public NodeConsumer() {
        super("NodeExecutor", "Polls on primary queue to execute node-messages.");
    }
}
