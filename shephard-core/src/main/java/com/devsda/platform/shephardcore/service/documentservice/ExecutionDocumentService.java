package com.devsda.platform.shephardcore.service.documentservice;

import com.devsda.platform.shephardcore.model.ShephardConfiguration;
import com.devsda.platform.shepherd.model.ExecuteWorkflowRequest;
import com.devsda.platform.shepherd.util.DateUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import static com.mongodb.client.model.Updates.*;

public class ExecutionDocumentService {

    private static final Logger log = LoggerFactory.getLogger(ExecutionDocumentService.class);
    @Inject
    private ShephardConfiguration shepherdConfiguration;
    private MongoClient mongoClient;

    public boolean insertExecutionDetails(ExecuteWorkflowRequest executeWorkflowRequest, Map<String, Object> initialPayload) {
        this.mongoClient = getMongoClient();
        try {
            ObjectMapper mapper = new ObjectMapper();
            ExecutionDetailsMetaData metaData = generateExecutionDetailsMetaData(executeWorkflowRequest);
            mapper.configure(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            String jsonString = "";
            String metaDataString= "";
            try {
                jsonString = mapper.writeValueAsString(initialPayload);
                metaDataString= mapper.writeValueAsString(metaData);
            } catch (JsonProcessingException e) {
                log.error(String.format("Unable to Process the initial payload for execution id : %s.", executeWorkflowRequest.getExecutionId()), e);
                return false;
            }
            MongoCollection<Document> collection = ExecutionDocumentServiceHelper.getMongoCollection(this.mongoClient, this.shepherdConfiguration.getDataSourceDetails().getDbname(), this.shepherdConfiguration.getDataSourceDetails().getCollectionname());

            if (collection != null) {
                final Document dbObjectInput = new Document();
                dbObjectInput.append("executionData",Document.parse(jsonString));
                dbObjectInput.append("executionMetaData", Document.parse(metaDataString));
                collection.insertOne(dbObjectInput);
                log.debug(String.format("Object : %s inserted successfully in \n collection : %s and db : %s", jsonString, this.shepherdConfiguration.getDataSourceDetails().getCollectionname(), this.shepherdConfiguration.getDataSourceDetails().getDbname()));
                return true;
            }
        } catch (MongoWriteException e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    private ExecutionDetailsMetaData generateExecutionDetailsMetaData(ExecuteWorkflowRequest executeWorkflowRequest) {
        ExecutionDetailsMetaData metaData = new ExecutionDetailsMetaData();
        metaData.setClientId(executeWorkflowRequest.getClientId());
        metaData.setExecutionId(executeWorkflowRequest.getExecutionId());
        metaData.setExecutionStartDateTime(DateUtil.currentDate());
        return metaData;
    }

    public Document fetchExecutionDetails(String executionID) throws Exception {
        try {
            MongoCollection<Document> collection = ExecutionDocumentServiceHelper.getMongoCollection(this.mongoClient, this.shepherdConfiguration.getDataSourceDetails().getDbname(), this.shepherdConfiguration.getDataSourceDetails().getCollectionname());
            if (collection != null) {
                ExecutionDetailsMetaData metaData = new ExecutionDetailsMetaData(executionID);
                Document result = collection.find(getSearchFilter(executionID)).first();
                return result;
            }
        } catch (Exception ex) {
            throw ex;
        }
        return null;
    }

    public boolean updateExecutionDetails(String executionID, Map<String, Object> updatedInput) {
        try {
            MongoCollection<Document> collection = ExecutionDocumentServiceHelper.getMongoCollection(this.mongoClient, this.shepherdConfiguration.getDataSourceDetails().getDbname(), this.shepherdConfiguration.getDataSourceDetails().getCollectionname());

            if (collection != null) {
                UpdateResult updateResult = collection.updateOne(getSearchFilter(executionID), getUpdateOperation(updatedInput), new UpdateOptions().upsert(true));
                log.debug("updateDocument() :: database: " + this.shepherdConfiguration.getDataSourceDetails().getDbname() + " and collection: " + this.shepherdConfiguration.getDataSourceDetails().getCollectionname()
                        + " is document Updated :" + updateResult.wasAcknowledged());
                boolean ack = updateResult.wasAcknowledged();
                return ack;
            }
        } catch (MongoWriteException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception ex) {
            throw ex;
        }
        return false;
    }

    private Document getUpdateOperation(Map<String, Object> updatedInput){
        Document updateOperation = new Document();
        for (Map.Entry<String,Object> entry : updatedInput.entrySet())
        {
            updateOperation.append("$set", new Document("executionData."+entry.getKey(), entry.getValue()));
        }
        updateOperation.append("$set", new Document("executionMetaData.lastModifiedDate", DateUtil.currentDate()), );
        updateOperation.append("$inc", new Document("executionMetaData.numberOfUpdatesAfterInsertion",1));
        return updateOperation;
    }

    private Document getSearchFilter(String executionID){
        Document dbObjectInput = new Document();
        dbObjectInput.append("executionMetaData.executionId",executionID);
        return dbObjectInput;

    }

    private MongoClient getMongoClient() {
        String mongoConnectionUri = ExecutionDocumentServiceHelper.createMongoConnectionUri(this.shepherdConfiguration.getDataSourceDetails());
        MongoClientURI uri = new MongoClientURI(mongoConnectionUri);
        return new MongoClient(uri);
    }
}
