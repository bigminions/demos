package com.githup.ussheepsheep.mongo;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
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
        logger.info("update many documents : ----------------------------------------------- -----------------------------------------------\n");
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
}
