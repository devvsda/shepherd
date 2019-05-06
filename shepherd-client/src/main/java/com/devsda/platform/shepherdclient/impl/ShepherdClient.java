package com.devsda.platform.shepherdclient.impl;

import com.devsda.platform.shepherd.exception.GraphLoaderException;
import com.devsda.platform.shepherd.exception.ShepherdInternalException;
import com.devsda.platform.shepherd.model.*;
import com.devsda.platform.shepherdclient.constants.Environment;
import com.devsda.platform.shepherdclient.constants.ShepherdClientConstants;
import com.devsda.platform.shepherdclient.loader.JSONLoader;
import com.devsda.platform.shepherdclient.loader.XMLLoader;
import com.devsda.platform.shepherdclient.loader.YAMLLoader;
import com.devsda.platform.shepherd.model.ServerDetails;
import com.devsda.platform.shepherdclient.model.ShepherdServerConfiguration;
import com.devsda.utils.httputils.HttpMethod;
import com.devsda.utils.httputils.constants.Protocol;
import com.devsda.utils.httputils.loader.JsonLoader;
import com.devsda.utils.httputils.methods.HttpGetMethod;
import com.devsda.utils.httputils.methods.HttpPostMethod;
import com.mongodb.*;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import org.bson.Document;
import org.bson.types.ObjectId;
import com.mongodb.client.result.UpdateResult;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mongodb.client.model.Filters.eq;


public class ShepherdClient {


    private static final Logger log = LoggerFactory.getLogger(ShepherdClient.class);

    private Environment environment;
    private ShepherdServerConfiguration shepherdServerDetails;
    private ShepherdClientHelper shepherdClientHelper;

    private MongoClient mongoClient;


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
            ShepherdResponse shepherdResponse = httpMethod.call(
                    serverDetails.getProtocol(), serverDetails.getHostName(), serverDetails.getPort(),
                    ShepherdClientConstants.Resources.REGISTER_CLIENT, null,
                    shepherdServerDetails.getHeaders(), new StringEntity(JsonLoader.loadObject(registerClientRequest)),
                    ShepherdResponse.class);
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
            EndpointRequest registerEndpointRequest = shepherdClientHelper.createEndpointRequest(clientName, endpointName, graphData, strigifiedEndpointDetails);
            ServerDetails serverDetails = shepherdServerDetails.getServerDetails();
            HttpMethod httpMethod = new HttpPostMethod();
            ShepherdResponse shepherdResponse = httpMethod.call(
                    serverDetails.getProtocol(), serverDetails.getHostName(), serverDetails.getPort(),
                    ShepherdClientConstants.Resources.REGISTER_ENDPOINT, null,
                    shepherdServerDetails.getHeaders(), new StringEntity(JsonLoader.loadObject(registerEndpointRequest)),
                    ShepherdResponse.class);
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


    public ShepherdResponse updateWorkflowDetails(String clientName, String endpointName, String workflowPath) {

        try {
            String graphData = XMLLoader.strigify(workflowPath);
            EndpointRequest updateEndpointRequest = shepherdClientHelper.createEndpointRequest(clientName, endpointName, graphData, null);
            ServerDetails serverDetails = shepherdServerDetails.getServerDetails();
            HttpMethod httpMethod = new HttpPostMethod();
            ShepherdResponse shepherdResponse = httpMethod.call(
                    serverDetails.getProtocol(), serverDetails.getHostName(), serverDetails.getPort(),
                    ShepherdClientConstants.Resources.UPDATE_WORKFLOW_DETAILS, null,
                    shepherdServerDetails.getHeaders(), new StringEntity(JsonLoader.loadObject(updateEndpointRequest)),
                    ShepherdResponse.class);
            return shepherdResponse;
        } catch(TransformerException | SAXException | ParserConfigurationException ex) {
            log.error("Problem in stringifying of xml file.", ex);
            throw new GraphLoaderException("Problem in stringifying of xml file.", ex);
        } catch(IOException ex) {
            log.error("Problem in loading graphDetails/endpointDetails from xml/json file.", ex);
            throw new GraphLoaderException("Problem in loading graphDetails/endpointDetails from xml/json file.", ex);
        } catch(URISyntaxException ex) {
            log.error(String.format("Problem while hitting Update Workflow Details API for client : %s, endpointName : %s", clientName, endpointName), ex);
            throw new ShepherdInternalException(String.format("Problem while hitting Update Workflow Details API for client : %s, endpointName : %s", clientName, endpointName), ex);
        }

    }

    public ShepherdResponse updateEndpointDetails(String clientName, String endpointName, String endpointPath) {

        try {
            GraphConfiguration graphConfiguration = JSONLoader.load(endpointPath, GraphConfiguration.class);
            String strigifiedEndpointDetails = JSONLoader.stringify(graphConfiguration);
            EndpointRequest updateEndpointRequest = shepherdClientHelper.createEndpointRequest(clientName, endpointName, null, strigifiedEndpointDetails);
            ServerDetails serverDetails = shepherdServerDetails.getServerDetails();
            HttpMethod httpMethod = new HttpPostMethod();
            ShepherdResponse shepherdResponse = httpMethod.call(
                    serverDetails.getProtocol(), serverDetails.getHostName(), serverDetails.getPort(),
                    ShepherdClientConstants.Resources.UPDATE_ENDPOINT_DETAILS, null,
                    shepherdServerDetails.getHeaders(), new StringEntity(JsonLoader.loadObject(updateEndpointRequest)),
                    ShepherdResponse.class);
            return shepherdResponse;
        } catch(IOException ex) {
            log.error("Problem in loading graphDetails/endpointDetails from xml/json file.", ex);
            throw new GraphLoaderException("Problem in loading graphDetails/endpointDetails from xml/json file.", ex);
        } catch(URISyntaxException ex) {
            log.error(String.format("Problem while hitting Update Endpoint Details API for client : %s, endpointName : %s", clientName, endpointName), ex);
            throw new ShepherdInternalException(String.format("Problem while hitting Update Endpoint Details API for client : %s, endpointName : %s", clientName, endpointName), ex);
        }

    }

    public ShepherdResponse retrieveEndpoint(String clientName, String endpointName) {

        try {
            Map<String, String> parameters = new HashMap<>();
            parameters.put("clientName", clientName);
            parameters.put("endpointName", endpointName);

            ServerDetails serverDetails = shepherdServerDetails.getServerDetails();

            HttpMethod httpMethod = new HttpGetMethod();
            ShepherdResponse shepherdResponse = httpMethod.call(serverDetails.getProtocol(), serverDetails.getHostName(), serverDetails.getPort(), ShepherdClientConstants.Resources.RETRIEVE_ENDPOINT, parameters, shepherdServerDetails.getHeaders(), null, ShepherdResponse.class);
            return shepherdResponse;
        } catch (Exception e) {
            log.error("Retrival endpoint failed", e);
            throw new ShepherdInternalException("dscasdc");
        }
    }

    public ShepherdResponse executeEndpoint(String clientName, String endpointName, Map<String, Object> initialPayload) {

        try {

            ExecuteWorkflowRequest executeWorkflowRequest = shepherdClientHelper.createExecuteWorkflowRequest(clientName, endpointName, initialPayload);

            ServerDetails serverDetails = shepherdServerDetails.getServerDetails();

            // Call Shepherd Server.
            HttpMethod httpMethod = new HttpPostMethod();
            ShepherdResponse shepherdResponse = httpMethod.call(serverDetails.getProtocol(),
                    serverDetails.getHostName(), serverDetails.getPort(),
                    ShepherdClientConstants.Resources.EXECUTE_ENDPOINT,
                    null, shepherdServerDetails.getHeaders(),
                    new StringEntity(JSONLoader.stringify(executeWorkflowRequest)), ShepherdResponse.class);
            // Return response.
            return shepherdResponse;

        } catch(IOException e) {
            log.error("Failed to stringify object.", e);
            throw new ShepherdInternalException(e);
        } catch(URISyntaxException e) {
            log.error("Failed to hit given endpoint", e);
            throw new ShepherdInternalException(e);
        }

    }
}
