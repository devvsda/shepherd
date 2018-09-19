package com.devsda.platform.shephardcore.service;

import com.devsda.platform.shepherd.model.NodeConfiguration;
import com.devsda.platform.shephardcore.model.NodeResponse;
import com.devsda.platform.shepherd.model.ServerDetails;
import com.devsda.utils.httputils.methods.HttpPostMethod;
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


        log.info(String.format("Executing node : %s. NodeConfiguration : %s, Server details : %s", nodeConfiguration.getName(), nodeConfiguration, serverDetails));

        String response = null;

        try {

            response = new HttpPostMethod().call(serverDetails.getHostName(), serverDetails.getPort(),
                    nodeConfiguration.getURI(), null, nodeConfiguration.getHeaders(),
                    "", String.class);

            log.info(String.format("Response of Node : %s is %s", nodeConfiguration.getName(), response));

            Thread.sleep(10);

        } catch(InterruptedException e) {
            log.error("Thread interrupted by third party", e);
        }

        return new NodeResponse(nodeConfiguration.getName(), response);
    }
}
