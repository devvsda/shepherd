package com.devsda.platform.shephardcore.service;

import com.devsda.platform.shephardcore.model.ShephardConfiguration;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Map;

public class ExecutionDocumentService {

    @Inject
    private ShephardConfiguration shepherdConfiguration;

    private MongoClient mongoClient;

    private static final Logger log = LoggerFactory.getLogger(ExecutionDocumentService.class);

    public boolean insertExecutionDetails(Integer executionId, Map<String, Object> initialPayload) {
        this.mongoClient = getMongoClient();
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS , false);
            String jsonString = "";
            try {
                jsonString = mapper.writeValueAsString(initialPayload);
            }catch(JsonProcessingException e){
                log.error(String.format("Unable to Process the initial payload for execution id : %s.", executionId), e);
                return false;
            }
            MongoCollection<Document> collection = ExecutionDocumentServiceHelper.getMongoCollection(this.mongoClient, this.shepherdConfiguration.getDataSourceDetails().getDbname(), this.shepherdConfiguration.getDataSourceDetails().getCollectionname());

            if (collection != null) {
                final Document dbObjectInput = Document.parse(jsonString);
                dbObjectInput.append("exec_id",executionId);
                dbObjectInput.append("createdAt", new Date());
                collection.insertOne(dbObjectInput);
                log.debug(String.format("Object : %s inserted successfully in \n collection : %s and db : %s", jsonString, this.shepherdConfiguration.getDataSourceDetails().getCollectionname(), this.shepherdConfiguration.getDataSourceDetails().getDbname()));
                return true;
            }
        } catch (MongoWriteException e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    public Document fetchExecutionDetails(Integer executionID) throws Exception {
        try
        {
            MongoCollection<Document> collection = ExecutionDocumentServiceHelper.getMongoCollection(this.mongoClient, this.shepherdConfiguration.getDataSourceDetails().getDbname(), this.shepherdConfiguration.getDataSourceDetails().getCollectionname());
            if (collection != null) {
                Document result = collection.find(new Document().append("exec_id",executionID)).first();
                return result;
            }
        }catch(Exception ex){
            throw ex;
        }
        return null;
    }

    public boolean updateExecutionDetails(Integer executionID, String updatedInput) {
        try {
            MongoCollection<Document> collection = ExecutionDocumentServiceHelper.getMongoCollection(this.mongoClient, this.shepherdConfiguration.getDataSourceDetails().getDbname(), this.shepherdConfiguration.getDataSourceDetails().getCollectionname());

            if (collection != null) {
                Document dbObjectInput =  new Document();
                dbObjectInput.append("exec_id",executionID);
                final Document dbObjectUpdateInput = Document.parse(updatedInput);
                // TODO: add execution_metadata : {executionID,clientID}
                dbObjectUpdateInput.append("exec_id",executionID);
                dbObjectUpdateInput.append("lastUpdatedDate",new Date());
                UpdateResult updateResult = collection.replaceOne(dbObjectInput, dbObjectUpdateInput);
                log.debug("updateDocument() :: database: " + this.shepherdConfiguration.getDataSourceDetails().getDbname() + " and collection: " + this.shepherdConfiguration.getDataSourceDetails().getCollectionname()
                        + " is document Updated :" + updateResult.wasAcknowledged());
                boolean ack = updateResult.wasAcknowledged();
                return ack;
            }
        } catch (MongoWriteException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception ex){
            throw ex;
        }
        return false;
    }

    private MongoClient getMongoClient() {
        String mongoConnectionUri = ExecutionDocumentServiceHelper.createMongoConnectionUri(this.shepherdConfiguration.getDataSourceDetails());
        MongoClientURI uri = new MongoClientURI(mongoConnectionUri);
        return new MongoClient(uri);
    }
}
