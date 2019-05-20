package com.devsda.platform.shepherdcore.consumer;

import com.devsda.platform.shepherdcore.model.ShepherdConfiguration;
import io.dropwizard.cli.ConfiguredCommand;
import io.dropwizard.setup.Bootstrap;
import net.sourceforge.argparse4j.inf.Namespace;

public class NodeTrackerConsumer extends ConfiguredCommand<ShepherdConfiguration> {

    @Override
    protected void run(Bootstrap<ShepherdConfiguration> bootstrap, Namespace namespace, ShepherdConfiguration shephardConfiguration) throws Exception {

    }

    public NodeTrackerConsumer() {
        super("NodeTrackerConsumer", "Helps node-messages to promote from secondary to primary queue.");
    }
}
