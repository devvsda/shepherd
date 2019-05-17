package com.devsda.platform.shepherdcore.consumer;

import com.devsda.platform.shepherdcore.application.ShepherdApplication;
import com.devsda.platform.shepherdcore.model.ShepherdConfiguration;
import com.google.inject.Injector;
import io.dropwizard.cli.ConfiguredCommand;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import net.sourceforge.argparse4j.inf.Namespace;

public class NodeConsumer extends ConfiguredCommand<ShepherdConfiguration> {

    @Override
    protected void run(Bootstrap<ShepherdConfiguration> bootstrap, Namespace namespace, ShepherdConfiguration shepherdConfiguration) throws Exception {
        Environment environment = new Environment(bootstrap.getApplication().getName(),
                bootstrap.getObjectMapper(), bootstrap.getValidatorFactory().getValidator(),
                bootstrap.getMetricRegistry(), bootstrap.getClassLoader());
        shepherdConfiguration.getMetricsFactory().configure(environment.lifecycle(),
                bootstrap.getMetricRegistry());
        bootstrap.run(shepherdConfiguration, environment);
        
        Injector injector =  new ShepherdApplication().createInjector(shepherdConfiguration, environment);

        SampleExecutor sampleExecutor = injector.getInstance(SampleExecutor.class);
        sampleExecutor.sample();
    }

    public NodeConsumer() {
        super("NodeExecutor", "Polls on primary queue to execute node-messages.");
    }
}
