package com.devsda.platform.shephardcore.service;

import com.devsda.platform.shephardcore.model.NodeConfiguration;
import com.devsda.platform.shephardcore.model.NodeResponse;
import com.devsda.platform.shephardcore.model.ServerDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

public class NodeExecutor implements Callable<NodeResponse> {

    private static final Logger log = LoggerFactory.getLogger(NodeExecutor.class);

    private NodeConfiguration nodeConfiguration;
    private ServerDetails serverDetails;

    public NodeExecutor(NodeConfiguration nodeConfiguration, ServerDetails serverDetails) {
        this.nodeConfiguration = nodeConfiguration;
        this.serverDetails = serverDetails;
    }

    @Override
    public NodeResponse call() throws Exception {

        log.info(String.format("NodeConfiguration : %s", this.nodeConfiguration));
        log.info(String.format("Server Details :%s", this.serverDetails));

        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            log.error("Thread interrupted by third party", e);
        }

        return new NodeResponse(nodeConfiguration.getName(), null);
    }
}
