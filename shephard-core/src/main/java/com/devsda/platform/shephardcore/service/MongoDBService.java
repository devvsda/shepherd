package com.devsda.platform.shephardcore.service;

import com.devsda.platform.shephardcore.model.ShephardConfiguration;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Map;

public class MongoDBService {

    @Inject
    private ShephardConfiguration shepherdConfiguration;

    private MongoClient mongoClient;

    private static final Logger log = LoggerFactory.getLogger(ExecuteWorkflowService.class);

    public boolean insertExecutionDetails(Integer executionId, Map<String, Object> initialPayload) {
        this.mongoClient = getMongoClient();
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS , false);
            String jsonString = "";
            try {
                jsonString = mapper.writeValueAsString(initialPayload);
            }catch(JsonProcessingException ex){
                log.error("unable to Process the intial payload.");
                return false;
            }
            MongoCollection<Document> collection = MongoDBServiceHelper.getMongoCollection(this.mongoClient, this.shepherdConfiguration.getDataSourceDetails().getDb_name(), this.shepherdConfiguration.getDataSourceDetails().getCollection_name());

            if (collection != null) {
                final Document dbObjectInput = Document.parse(jsonString);
                dbObjectInput.append("exec_id",executionId);
                dbObjectInput.append("createdAt", new Date());
                collection.insertOne(dbObjectInput);
                log.debug("Object : " + jsonString);
                log.debug("inserted successfully in ");
                log.debug("collection : " + this.shepherdConfiguration.getDataSourceDetails().getCollection_name() + "and db : " + this.shepherdConfiguration.getDataSourceDetails().getDb_name());
                return true;
            }
        } catch (MongoWriteException e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    private MongoClient getMongoClient() {
        String mongoConnectionUri = MongoDBServiceHelper.createMongoConnectionUri(this.shepherdConfiguration.getDataSourceDetails());
        MongoClientURI uri = new MongoClientURI(mongoConnectionUri);
        return new MongoClient(uri);
    }
}
