package com.devsda.platform.shephardcore.consumer;

import com.devsda.platform.shephardcore.model.ShephardConfiguration;
import io.dropwizard.cli.ConfiguredCommand;
import io.dropwizard.setup.Bootstrap;
import net.sourceforge.argparse4j.inf.Namespace;

public class NodeTrackerConsumer extends ConfiguredCommand<ShephardConfiguration> {

    @Override
    protected void run(Bootstrap<ShephardConfiguration> bootstrap, Namespace namespace, ShephardConfiguration shephardConfiguration) throws Exception {

    }

    public NodeTrackerConsumer() {
        super("NodeTrackerConsumer", "Helps node-messages to promote from secondary to primary queue.");
    }
}
