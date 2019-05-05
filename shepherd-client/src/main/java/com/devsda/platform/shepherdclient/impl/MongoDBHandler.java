package com.devsda.platform.shepherdclient.impl;

import com.devsda.platform.shepherd.model.DataSourceDetails;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.MongoCommandException;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MongoDBHandler {

    private static final Logger log = LoggerFactory.getLogger(MongoDBHandler.class);

    public static String createMongoConnectionUri(DataSourceDetails dataSourceDetails) {
        StringBuilder uriBuilder = new StringBuilder("");
        uriBuilder.append(dataSourceDetails.getPrefix()).append("://").append(dataSourceDetails.getUser()).append(":").append(dataSourceDetails.getPassword()).append("@");

        Iterator<String> it = dataSourceDetails.getClusters().iterator();

        if(it.hasNext()){
            uriBuilder.append(it.next());
        }

        while(it.hasNext()){
            uriBuilder.append(",").append(it.next());
        }

        uriBuilder.append(dataSourceDetails.getPath()).append("?").append(dataSourceDetails.getParams());
        String hello = uriBuilder.toString();
        return uriBuilder.toString();
    }

    public static MongoCollection<Document> getMongoCollection(MongoClient mongoClient, String dataBaseName, String collectionName) {
        if (mongoClient == null)
            return null;
        MongoDatabase db = mongoClient.getDatabase(dataBaseName);
        MongoCollection<Document> collection = db.getCollection(collectionName);
        return collection;
    }

}
