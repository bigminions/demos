package com.githup.bigminons.mongo;

import com.mongodb.BasicDBList;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.*;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by daren on 2017/4/19.
 */
public class MongoDb {

    private static final String HOST = "localhost";

    private static final Integer PORT = 27017;

    private static final Logger logger = LoggerFactory.getLogger(MongoDb.class);

    private static final MongoClient mongoClient;

    private static final MongoDatabase database;

    private static final MongoCollection<Document> collection;

    private static final Block<Document> block = document -> logger.info("print {} ", document.toJson());

    static {
        mongoClient = new MongoClient(HOST, PORT);
        database = mongoClient.getDatabase("mydb");
        collection = database.getCollection("user");
    }

    public static void main(String[] args) {
        insert();
        insert();
        find();
        update();
        delete();
        validation();
        index();
        projection();
        info();
        aggregation();
        textSearch();
        runCommand();
        other();
        database.drop();
    }

    public static void insert() {
        Long startCnt = collection.count();
        logger.info("insert one document to collection");
        Document document = new Document("id", 10001).append("name", "daren").append("email", "123@gmail.com");
        collection.insertOne(document);
        logger.info("insert many documents to collection");
        List<Document> documents = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            documents.add(new Document("id", i + 20000).append("name", "test" + i));
        }
        collection.insertMany(documents);
        Long endCnt = collection.count();
        logger.info("startCnt = {}, endCnt = {}, cnt = {}", startCnt, endCnt, endCnt - startCnt);
    }

    public static void find() {
        Document document = collection.find().first();
        logger.info("find the document : {}", document.toJson());

        logger.info("find many documents : ----------------------------------------------- -----------------------------------------------");
        MongoCursor<Document> cursor = collection.find().iterator();
        while (cursor.hasNext()) {
            logger.info(" --------------------- {}", cursor.next().toJson());
        }
        logger.info("find many documents : ----------------------------------------------- -----------------------------------------------\n");

        Document document1 = collection.find(Filters.eq("name", "test66")).first();
        logger.info("find the document that name is test66 : {}", document1);
    }

    public static void update() {
        Document document = collection.find(Filters.eq("name", "test66")).first();
        logger.info("find the document name is test66 : {}", document.toJson());

        collection.updateOne(Filters.eq("name", "test66"), new Document("$set", new Document("name", "test66").append("updated", "true")));
        document = collection.find(Filters.eq("name", "test66")).first();
        logger.info("update the document : {}", document.toJson());

        logger.info("find many documents : ----------------------------------------------- -----------------------------------------------");
        MongoCursor<Document> cursor = collection.find(Filters.eq("name", "test77")).iterator();
        while (cursor.hasNext()) {
            logger.info(" --------------------- {}", cursor.next().toJson());
        }
        collection.updateMany(Filters.eq("name", "test77"), new Document("$set", new Document("name", "test77").append("updated", "true")));
        logger.info("update many documents : ----------------------------------------------- -----------------------------------------------");
        cursor = collection.find(Filters.eq("name", "test77")).iterator();
        while (cursor.hasNext()) {
            logger.info(" --------------------- {}", cursor.next().toJson());
        }
    }

    public static void delete() {
        logger.info("delete the name is test66");
        collection.deleteMany(Filters.eq("name", "test66"));
        Document document = collection.find(Filters.eq("name", "test66")).first();
        logger.info("find the document name is test66: {}", document);
    }

    public static void validation() {
        String validCollName = "valid_test";
        MongoCollection<Document> validColl = database.getCollection(validCollName);
        if (validColl != null) {
            validColl.drop();
        }
        ValidationOptions options = new ValidationOptions().validator(Filters.exists("email")).validator(Filters.exists("phone"));
        database.createCollection(validCollName, new CreateCollectionOptions().validationOptions(options));
        validColl = database.getCollection(validCollName);
        try {
            validColl.insertOne(new Document("name", "test"));
        } catch (MongoWriteException e) {
            logger.error("insert into validation collection is error : only name");
        }
        try {
            validColl.insertOne(new Document("email", "test@gmail.com"));
        } catch (MongoWriteException e) {
            logger.error("insert into validation collection is error : only email");
        }
        try {
            validColl.insertOne(new Document("email", "test@gmail.com").append("phone", "110"));
        } catch (MongoWriteException e) {
            logger.error("insert into validation collection is error : has email and phone");
        }
    }

    public static void index() {
        collection.createIndex(Indexes.ascending("name"));
        collection.createIndex(Indexes.text("name"));
        MongoCursor<Document> cursor = collection.listIndexes().iterator();
        while (cursor.hasNext()) {
            logger.info("index {}", cursor.next().toJson());
        }
    }

    public static void projection() {
        collection.insertOne(new Document("name", "Sony").append("ico", "https://avatars2.githubusercontent.com/u/13105090?v=3&s=460").append("phone", "123").append("email", "123@gmail.com"));
        collection.find(Filters.and(Filters.eq("name", "Sony"))).projection(new Document("ico", "none")).forEach(block);
    }

    public static void info() {
        logger.info("database info ------------------- ");
        for (String collName : database.listCollectionNames()) {
            logger.info("database collection name : {}", collName);
        }
    }

    public static void aggregation() {
        logger.info("aggregation ------------------- ");
        List<Document> documents = new ArrayList<>();
        BasicDBList classList = new BasicDBList();
        classList.addAll(Arrays.asList("math", "eng", "music"));
        documents.add(new Document("name", "John").append("age", 18).append("from", "China").append("math", 99).append("eng", 99).append("class", classList));
        documents.add(new Document("name", "Tony").append("age", 18).append("from", "China").append("math", 98).append("eng", 98).append("class", classList));
        documents.add(new Document("name", "Jack").append("age", 18).append("from", "China").append("math", 97).append("eng", 97).append("class", classList));
        documents.add(new Document("name", "Penny").append("age", 17).append("from", "China").append("math", 96).append("eng", 96).append("class", classList));
        collection.insertMany(documents);
        collection.aggregate(Arrays.asList(
                Aggregates.match(Filters.eq("age", 18)),
                Aggregates.group("$age", Accumulators.avg("math_avg", "$math"))
        )).forEach(block);
    }

    public static void textSearch() {
        logger.info("text search ------------------- ");
        MongoCollection<Document> collection = database.getCollection("text_search_test");
        collection.createIndex(Indexes.text("twiter"));
        collection.insertOne(new Document("name", "Tom").append("twiter", "My name is Tom, I like apple and orange"));
        collection.insertOne(new Document("name", "Tony").append("twiter", "My name is Tom, I like apple and peach"));
        collection.insertOne(new Document("name", "Jack").append("twiter", "My name is Tom, I like peach and orange"));
        logger.info("key word : apple");
        collection.find(Filters.text("apple")).forEach(block);
        logger.info("key word : apple orange");
        collection.find(Filters.text("apple orange")).forEach(block);
    }

    public static void runCommand() {
        logger.info("run command ------------------- ");
        Document buildInfoResults = database.runCommand(new Document("buildInfo", 1));
        logger.info("build info results : {}", buildInfoResults.toJson());
    }

    public static void other() {
        Bson bson = Updates.combine(Updates.set("name", "set_name"), Updates.set("email", "set@gmail.com"), Updates.currentDate("updateAt"));
        logger.info(bson.toString());
    }
}
