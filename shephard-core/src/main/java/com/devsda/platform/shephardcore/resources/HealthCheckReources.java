package com.devsda.platform.shephardcore.resources;

import com.devsda.platform.shephardcore.constants.ShephardConstants;
import com.devsda.platform.shephardcore.model.HealthCheck;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.util.JSON;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Iterator;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mongodb.client.model.Filters.eq;

@Path(ShephardConstants.Resources.HealthCheck)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HealthCheckReources {
    private MongoClient mongoClient;
    private static final Logger log = LoggerFactory.getLogger(HealthCheckReources.class);

    @Path("/{echoText}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response healthCheck(@PathParam("echoText") String echoText) throws Exception {

        Map<String, String> response = new HashMap<>();

        response.put("echoString", echoText);
        response.put("hostName", InetAddress.getLocalHost().getHostName());

        ObjectMapper objectMapper = new ObjectMapper();
        String stringifyResponse = objectMapper.writeValueAsString(response);

        return Response.ok(stringifyResponse).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response healthCheckWithParameters(@QueryParam("echoText") String echoText) throws Exception {

        Map<String, String> response = new HashMap<>();

        MongoClientURI uri = new MongoClientURI(
                "mongodb://ashutosh:mOngo%407218@shepherd-cluster0-shard-00-00-lovgv.mongodb.net:27017,shepherd-cluster0-shard-00-01-lovgv.mongodb.net:27017,shepherd-cluster0-shard-00-02-lovgv.mongodb.net:27017/test?ssl=true&replicaSet=Shepherd-Cluster0-shard-0&authSource=admin&retryWrites=true");
        this.mongoClient = new MongoClient(uri);
        MongoDatabase database = mongoClient.getDatabase("shepherd");

        response.put("echoString", echoText);
        response.put("hostName", InetAddress.getLocalHost().getHostName());

        ObjectMapper objectMapper = new ObjectMapper();

//        MongoDatabase db = mongoClient.getDatabase("school");
//        System.out.println(db.toString());

        MongoCollection<Document> collectionAtlas = database.getCollection("execution_details");
        try {
            Document firstAtlasDoc = collectionAtlas.find(eq("exec_id", "123456789")).first();

            response.put("atlasResponse", firstAtlasDoc.get("exec_id",String.class));


            // Inserting in the DB
            collectionAtlas.insertOne(new Document(Document.parse("{\"name\":\"Ashu\", \"profession\":\"software developer\"}")));
            final Document dbObjectInput = Document.parse("{\"name\":\"Ashu\"}");

            // Updating in the DB
            final Document dbObjectUpdateInput = Document.parse("{\"name\":\"Ashu\", \"profession\":\"software1 developer\"}");
            UpdateResult update = collectionAtlas.replaceOne(dbObjectInput, dbObjectUpdateInput);
            log.debug("updateDocument() :: database: " + database.getName() + " and collection: " + collectionAtlas.toString()
                    + " is document Updated :" + update.wasAcknowledged());

        }catch (Exception ex){
//            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        FindIterable<Document> secondDoc = collectionAtlas.find();

//        Iterator it = secondDoc.iterator();

        long hello = collectionAtlas.countDocuments();
        MongoCursor<Document> cursor = secondDoc.iterator();
//        ArrayList<Document> list = new ArrayList<Document>();

        int j=0;
        while(cursor.hasNext()){
//            System.out.println(cursor.next().toJson());
            Document doc = cursor.next();
//            System.out.println(doc.toBsonDocument());
            response.put("jsonDocs" + j, doc.toJson());
            j++;
        }

//        int i = 0;
//        while(it.hasNext()){
//            response.put("allDocuments" + i, it.next().toString());
//            i++;
//        }

        response.put("fullDcoument", secondDoc.toString());

//        response.put("atlasInsert", secondDoc.get("profession",String.class));


//        boolean hello = insertDocument("shepherd","execution_details","{'name':'Ashu','profession':'software developer'}");

//        MongoCollection<Document> col = db.getCollection("students");

//        Document docs= col.find(eq("_id", new ObjectId("5c9f832297bc761fc969ac4f"))).first();

//        System.out.println(docs);
//        response.put("mongoDBresponse",docs.toJson());

        String stringifyResponse = objectMapper.writeValueAsString(response);
        return Response.ok(stringifyResponse).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response healthCheck(@NotNull HealthCheck healthCheck) throws Exception {

        Map<String, String> response = new HashMap<>();

        response.put("echoString", healthCheck.getEchoText());
        response.put("hostName", InetAddress.getLocalHost().getHostName());

        ObjectMapper objectMapper = new ObjectMapper();
        String stringifyResponse = objectMapper.writeValueAsString(response);

        return Response.ok(stringifyResponse).build();
    }


    public boolean insertDocument(String dataBaseName, String collectionName, String input) {
        try {
            MongoCollection<Document> collection = getMongoCollection("shepherd", "execution_details");
            if (collection != null) {
                final Document dbObjectInput = Document.parse(input);
                collection.insertOne(dbObjectInput);
                log.debug("Object : " + input);
                log.debug("inserted successfully in ");
                log.debug("collection : " + collectionName + "and db : " + dataBaseName);
                return true;
            }
        } catch (MongoWriteException e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    public ArrayList<String> find(String dataBaseName, String collectionName, String condition) {
        ArrayList<String> result = new ArrayList<>();

        log.debug("Find and retrieve data from database." + "\nDatabase: " + dataBaseName + "\nCollection: "
                + collectionName + "\nCondition/Query: " + condition);

        try {
            MongoCollection<Document> collection = getMongoCollection(dataBaseName, collectionName);
            if (collection != null) {
                collection.find(BasicDBObject.parse(condition)).forEach((Block<Document>) document -> {
                    result.add(JSON.serialize(document));
                });
                if (result.size() != 0) {
                    log.debug("find() :: database: " + dataBaseName + " and collection: " + collectionName
                            + " fetched No of :" + result.size());
                } else {
                    log.debug("find() :: database: " + dataBaseName + " and collection: " + collectionName
                            + " documents are not found");
                }
            } else {
                log.debug("Collection " + collectionName + " is empty in database " + dataBaseName);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return result;
    }


    private MongoCollection<Document> getMongoCollection(String dataBaseName, String collectionName) {
        if (mongoClient == null)
            return null;
        MongoDatabase db = mongoClient.getDatabase(dataBaseName);
        List<String> collectionList = db.listCollectionNames().into(new ArrayList<String>());
        if (!collectionList.contains(collectionName)) {
            log.debug("The requested database(" + dataBaseName + ") / collection(" + collectionName
                    + ") not available in mongodb, Creating ........");
            try {
                db.createCollection(collectionName);
            } catch (MongoCommandException e) {
                String message = "collection '" + dataBaseName + "." + collectionName + "' already exists";
                if (e.getMessage().contains(message)) {
                    log.warn("A " + message + ".");
                } else {
                    throw e;
                }
            }
            log.debug("done....");
        }
        MongoCollection<Document> collection = db.getCollection(collectionName);
        return collection;
    }

    public boolean updateDocument(String dataBaseName, String collectionName, String input, String updateInput) {
        try {
            MongoCollection<Document> collection = getMongoCollection(dataBaseName, collectionName);
            if (collection != null) {
                final Document dbObjectInput = Document.parse(input);
                final Document dbObjectUpdateInput = Document.parse(updateInput);
                UpdateResult updateMany = collection.replaceOne(dbObjectInput, dbObjectUpdateInput);
                log.debug("updateDocument() :: database: " + dataBaseName + " and collection: " + collectionName
                        + " is document Updated :" + updateMany.wasAcknowledged());
                return updateMany.wasAcknowledged();
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }
}