package com.devsda.platform.shepherdclient.impl;

import com.devsda.platform.shepherd.model.RegisterClientRequest;
import com.devsda.platform.shepherdclient.constants.Environment;
import com.devsda.platform.shepherdclient.constants.ShepherdClientConstants;
import com.devsda.platform.shepherdclient.loader.YAMLLoader;
import com.devsda.platform.shepherdclient.model.ShepherdServerConfiguration;
import com.devsda.utils.httputils.HttpMethod;
import com.devsda.utils.httputils.methods.HttpPostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

public class ShepherdClient {


    private static final Logger log = LoggerFactory.getLogger(ShepherdClient.class);

    private Environment environment;
    private ShepherdServerConfiguration shepherdServerDetails;
    private ShepherdClientHelper shepherdClientHelper;

    public ShepherdClient(Environment environment) throws IOException {

        this.environment = environment;

        String shepherdServerConfigurationFile = ShepherdClientConstants.CONFIGURATION_FILE_NAME.replace(ShepherdClientConstants.PlaceHolders.Environment, this.environment.name().toLowerCase());
        this.shepherdServerDetails = YAMLLoader.load(shepherdServerConfigurationFile, ShepherdServerConfiguration.class);
        this.shepherdClientHelper = new ShepherdClientHelper();
    }



    public void registerClient(String clientName) {

        log.info(String.format("Going to register client with name : %s", clientName));
        RegisterClientRequest registerClientRequest = new RegisterClientRequest();
        HttpMethod httpMethod = new HttpPostMethod();



    }

    public void registerEndpoint(String clientName, String endpointName, String workflowPath, String endpointPath) {

    }

    public void executeEndpoint(String clientName, String endpointName, Map<String, Object> initialPayload) {

    }
}
