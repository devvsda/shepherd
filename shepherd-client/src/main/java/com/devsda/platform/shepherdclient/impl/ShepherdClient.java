package com.devsda.platform.shepherdclient.impl;

import com.devsda.platform.shepherd.exception.GraphLoaderException;
import com.devsda.platform.shepherd.exception.ShepherdInternalException;
import com.devsda.platform.shepherd.model.GraphConfiguration;
import com.devsda.platform.shepherd.model.RegisterClientRequest;
import com.devsda.platform.shepherd.model.RegisterEndpointRequest;
import com.devsda.platform.shepherd.model.ShepherdResponse;
import com.devsda.platform.shepherdclient.constants.Environment;
import com.devsda.platform.shepherdclient.constants.ShepherdClientConstants;
import com.devsda.platform.shepherdclient.loader.JSONLoader;
import com.devsda.platform.shepherdclient.loader.XMLLoader;
import com.devsda.platform.shepherdclient.loader.YAMLLoader;
import com.devsda.platform.shepherdclient.model.ServerDetails;
import com.devsda.platform.shepherdclient.model.ShepherdServerConfiguration;
import com.devsda.utils.httputils.HttpMethod;
import com.devsda.utils.httputils.loader.JsonLoader;
import com.devsda.utils.httputils.methods.HttpGetMethod;
import com.devsda.utils.httputils.methods.HttpPostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
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

    public ShepherdResponse registerClient(String clientName) {

        RegisterClientRequest registerClientRequest = null;

        try {
            log.info(String.format("Going to register client with name : %s", clientName));
            registerClientRequest = shepherdClientHelper.createRegisterClientRequest(clientName);
            ServerDetails serverDetails = shepherdServerDetails.getServerDetails();
            HttpMethod httpMethod = new HttpPostMethod();
            ShepherdResponse shepherdResponse = httpMethod.call(serverDetails.getHostname(), serverDetails.getPort(), ShepherdClientConstants.Resources.REGISTER_CLIENT, null, shepherdServerDetails.getHeaders(), JsonLoader.loadObject(registerClientRequest), ShepherdResponse.class);
            return shepherdResponse;
        } catch(IOException  e) {
            log.error(String.format("Problem while serialising Client Request : %s", registerClientRequest), e);
            throw new ShepherdInternalException(e);
        } catch(URISyntaxException e) {
            log.error(String.format("Problem while hitting Register Client API for client : %s", clientName), e);
            throw new ShepherdInternalException(String.format("Problem while hitting Register Client API for client : %s", clientName), e);
        }

    }

    public ShepherdResponse registerEndpoint(String clientName, String endpointName, String workflowPath, String endpointPath) {

        try {
            String graphData = XMLLoader.strigify(workflowPath);
            GraphConfiguration graphConfiguration = JSONLoader.load(endpointPath, GraphConfiguration.class);
            String strigifiedEndpointDetails = JSONLoader.stringify(graphConfiguration);
            RegisterEndpointRequest registerEndpointRequest = shepherdClientHelper.createregisterEndpointRequest(clientName, endpointName, graphData, strigifiedEndpointDetails);
            ServerDetails serverDetails = shepherdServerDetails.getServerDetails();
            HttpMethod httpMethod = new HttpPostMethod();
            ShepherdResponse shepherdResponse = httpMethod.call(serverDetails.getHostname(), serverDetails.getPort(), ShepherdClientConstants.Resources.REGISTER_ENDPOINT, null, shepherdServerDetails.getHeaders(), JsonLoader.loadObject(registerEndpointRequest), ShepherdResponse.class);
            return shepherdResponse;
        } catch(TransformerException | SAXException | ParserConfigurationException ex) {
            log.error("Problem in stringifying of xml file.", ex);
            throw new GraphLoaderException("Problem in stringifying of xml file.", ex);
        } catch(IOException ex) {
            log.error("Problem in loading graphDetails/endpointDetails from xml/json file.", ex);
            throw new GraphLoaderException("Problem in loading graphDetails/endpointDetails from xml/json file.", ex);
        } catch(URISyntaxException ex) {
            log.error(String.format("Problem while hitting Register Endpoint API for client : %s, endpointName : %s", clientName, endpointName), ex);
            throw new ShepherdInternalException(String.format("Problem while hitting Register Endpoint API for client : %s, endpointName : %s", clientName, endpointName), ex);
        }

    }

    public ShepherdResponse retrieveEndpoint(String clientName, String endpointName) {

        try {
            Map<String, String> parameters = new HashMap<>();
            parameters.put("clientName", clientName);
            parameters.put("endpointName", endpointName);

            ServerDetails serverDetails = shepherdServerDetails.getServerDetails();

            HttpMethod httpMethod = new HttpGetMethod();
            ShepherdResponse shepherdResponse = httpMethod.call(serverDetails.getHostname(), serverDetails.getPort(), ShepherdClientConstants.Resources.RETRIEVE_ENDPOINT, parameters, shepherdServerDetails.getHeaders(), null, ShepherdResponse.class);
            return shepherdResponse;
        } catch (Exception e) {
            log.error("Retrival endpoint failed", e);
            throw new ShepherdInternalException("dscasdc");
        }
    }

//    public void executeEndpoint(String clientName, String endpointName, Map<String, Object> initialPayload) {
//
//    }
}
