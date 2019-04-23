package com.devsda.platform.shephardcore.service;

import com.devsda.platform.shepherd.model.NodeConfiguration;
import com.devsda.platform.shephardcore.model.NodeResponse;
import com.devsda.platform.shepherd.model.ServerDetails;
import com.devsda.utils.httputils.constants.Protocol;
import com.devsda.utils.httputils.methods.HttpPostMethod;
import org.apache.http.entity.StringEntity;
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

        // 1. Create entry in Node table.
        // TODO

        // 2. Execute Node.
        response = new HttpPostMethod().call(Protocol.HTTPS, serverDetails.getHostName(), serverDetails.getPort(),
                nodeConfiguration.getURI(), null, nodeConfiguration.getHeaders(),
                new StringEntity(""), String.class);

        log.info(String.format("Response of Node : %s is %s", nodeConfiguration.getName(), response));

        // 3. Update Node status as Completed in Node table.
        // TODO

        return new NodeResponse(nodeConfiguration.getName(), response);
    }
}
