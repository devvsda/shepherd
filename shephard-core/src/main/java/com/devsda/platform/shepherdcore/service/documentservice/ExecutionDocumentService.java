package com.devsda.platform.shepherdcore.service.documentservice;

import com.devsda.platform.shepherdcore.loader.JSONLoader;
import com.devsda.platform.shepherdcore.model.ShepherdConfiguration;
import com.devsda.platform.shepherd.model.ExecuteWorkflowRequest;
import com.devsda.platform.shepherd.model.ExecutionData;
import com.devsda.platform.shepherd.util.DateUtil;
import com.google.inject.Inject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class ExecutionDocumentService {

    private static final Logger log = LoggerFactory.getLogger(ExecutionDocumentService.class);
    @Inject
    private ShepherdConfiguration shepherdConfiguration;
    private MongoClient mongoClient;

    public boolean insertExecutionDetails(ExecuteWorkflowRequest executeWorkflowRequest, String initialPayload) {
        if(this.mongoClient==null) {
            this.mongoClient = getMongoClient();
        }
        try {
            ExecutionDetailsMetaData metaData = generateExecutionDetailsMetaData(executeWorkflowRequest);
            String executionMetaDataJson= "";
            try {
                executionMetaDataJson= JSONLoader.stringify(metaData);
            } catch (IOException ex){
                log.error(String.format("Unable to Process the initial payload for execution id : %s.", executeWorkflowRequest.getExecutionId()), ex);
                return false;
            }
            MongoCollection<Document> collection = ExecutionDocumentServiceHelper.getMongoCollection(this.mongoClient, this.shepherdConfiguration.getDataSourceDetails().getDbname(), this.shepherdConfiguration.getDataSourceDetails().getCollectionname());

            if (collection != null) {
                final Document dbObjectInput = new Document();
                dbObjectInput.append(ExecutionDocumentConstants.Fields.EXECUTION_DATA_FIELD,Document.parse(initialPayload));
                dbObjectInput.append(ExecutionDocumentConstants.Fields.EXECUTION_METADATA_FIELD, Document.parse(executionMetaDataJson));
                collection.insertOne(dbObjectInput);
                log.debug(String.format("Object : %s inserted successfully in \n collection : %s and db : %s", initialPayload, this.shepherdConfiguration.getDataSourceDetails().getCollectionname(), this.shepherdConfiguration.getDataSourceDetails().getDbname()));
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
        metaData.setObjectId(executeWorkflowRequest.getObjectId());
        metaData.setExecutionStartDateTime(DateUtil.currentDate());
        return metaData;
    }

    public Document fetchExecutionDetails(String objectId, String executionID) throws Exception {
        if(this.mongoClient == null){
            this.mongoClient = getMongoClient();
        }
        try {
            MongoCollection<Document> collection = ExecutionDocumentServiceHelper.getMongoCollection(this.mongoClient, this.shepherdConfiguration.getDataSourceDetails().getDbname(), this.shepherdConfiguration.getDataSourceDetails().getCollectionname());
            if (collection != null) {
                Document result = collection.find(getSearchFilter(objectId, executionID)).first();
                return result;
            }
        } catch (Exception ex) {
            throw ex;
        }
        return null;
    }

    public boolean updateExecutionDetails(String objectId, String executionID, String updatedInput) throws Exception{
        if(this.mongoClient == null){
            this.mongoClient = getMongoClient();
        }
        try {
            MongoCollection<Document> collection = ExecutionDocumentServiceHelper.getMongoCollection(this.mongoClient, this.shepherdConfiguration.getDataSourceDetails().getDbname(), this.shepherdConfiguration.getDataSourceDetails().getCollectionname());

            if (collection != null) {
                UpdateResult updateResult = collection.updateOne(getSearchFilter(objectId,executionID), getUpdateOperationOnFullExecutionData(updatedInput), new UpdateOptions());
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
            updateOperation.append(ExecutionDocumentConstants.Operations.SET_OPERATION, new Document(ExecutionDocumentConstants.Fields.EXECUTION_DATA_FIELD+"."+entry.getKey(), entry.getValue()));
        }
        updateOperation.append(ExecutionDocumentConstants.Operations.SET_OPERATION, new Document(ExecutionDocumentConstants.Fields.EXECUTION_METADATA_FIELD + "." + ExecutionDocumentConstants.Fields.LAST_MODIFIED_DATE, DateUtil.currentDate()));
        updateOperation.append(ExecutionDocumentConstants.Operations.INCREMENT_OPERATION, new Document(ExecutionDocumentConstants.Fields.EXECUTION_METADATA_FIELD + "."+ ExecutionDocumentConstants.Fields.UPDATE_COUNT, 1));
        return updateOperation;
    }

    private Document getUpdateOperationOnFullExecutionData(String executionData) throws Exception{
        Document updateOperation = new Document();

        Document setDocument = new Document(ExecutionDocumentConstants.Fields.EXECUTION_DATA_FIELD, Document.parse(executionData));
        setDocument.append(ExecutionDocumentConstants.Fields.EXECUTION_METADATA_FIELD + "." + ExecutionDocumentConstants.Fields.LAST_MODIFIED_DATE,DateUtil.currentDate());
        updateOperation.append(ExecutionDocumentConstants.Operations.SET_OPERATION, setDocument);
        updateOperation.append(ExecutionDocumentConstants.Operations.INCREMENT_OPERATION, new Document(ExecutionDocumentConstants.Fields.EXECUTION_METADATA_FIELD + "." + ExecutionDocumentConstants.Fields.UPDATE_COUNT, 1));

        return updateOperation;
    }

    private Document getSearchFilter(String objectId, String executionID){
        Document dbObjectInput = new Document();
        dbObjectInput.append(ExecutionDocumentConstants.Fields.EXECUTION_METADATA_FIELD+ "."+ ExecutionDocumentConstants.Fields.EXCUTION_ID, executionID);
        dbObjectInput.append(ExecutionDocumentConstants.Fields.EXECUTION_METADATA_FIELD+ "."+ ExecutionDocumentConstants.Fields.OBJECT_ID, objectId);
        return dbObjectInput;

    }

    private MongoClient getMongoClient() {
        String mongoConnectionUri = ExecutionDocumentServiceHelper.createMongoConnectionUri(this.shepherdConfiguration.getDataSourceDetails());
        MongoClientURI uri = new MongoClientURI(mongoConnectionUri);
        return new MongoClient(uri);
    }
}
