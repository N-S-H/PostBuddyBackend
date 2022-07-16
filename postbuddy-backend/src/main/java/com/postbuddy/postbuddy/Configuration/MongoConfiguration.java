package com.postbuddy.postbuddy.Configuration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.postbuddy.postbuddy.Models.Entities.Comment;
import com.postbuddy.postbuddy.Models.Entities.Post;
import com.postbuddy.postbuddy.Models.Entities.User;
import lombok.extern.slf4j.Slf4j;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Profile("lcl")
@Configuration
@Slf4j
public class MongoConfiguration {

    @Value("${mongo.connectionNodes}")
    private String connectionNodes;

    @Value("${mongo.databaseName}")
    private String databaseName;

    @Value("${mongo.usersCollection}")
    private String usersCollection;

    @Value("${mongo.postsCollection}")
    private String postsCollection;

    @Value("${mongo.commentsCollection}")
    private String commentsCollection;

    @Value("${spring.application.name}")
    private String applicationName;

    @Bean
    public MongoClient getMongoClient() {
        ConnectionString connectionString = new ConnectionString(getConnectionString());
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder().applicationName(applicationName).applyConnectionString(connectionString).build();
        MongoClient mongoClient = MongoClients.create(mongoClientSettings);
        return mongoClient;
    }

    private String getConnectionString() {
        StringBuilder connectionBuilder = new StringBuilder();
        connectionBuilder.append("mongodb://").append(connectionNodes);
        return connectionBuilder.toString();
    }

    @Bean
    public MongoDatabase getDatabaseName(MongoClient mongoClient) {
        MongoDatabase mongoDatabase = mongoClient.getDatabase(databaseName);
        return mongoDatabase;
    }

    @Bean
    @Qualifier("postsCollection")
    public MongoCollection getPostsCollection(MongoDatabase mongoDatabase) {
        MongoCollection<Post> mongoCollection = mongoDatabase.getCollection(postsCollection,Post.class).withCodecRegistry(getCodecRegistry());
        return mongoCollection;
    }

    @Bean
    @Qualifier("commentsCollection")
    public MongoCollection getCommentsCollection(MongoDatabase mongoDatabase) {
        MongoCollection<Comment> mongoCollection = mongoDatabase.getCollection(commentsCollection, Comment.class).withCodecRegistry(getCodecRegistry());
        return mongoCollection;
    }

    @Bean
    @Qualifier("usersCollection")
    public MongoCollection getUsersCollection(MongoDatabase mongoDatabase) {
        MongoCollection<User> mongoCollection = mongoDatabase.getCollection(usersCollection, User.class).withCodecRegistry(getCodecRegistry());
        return mongoCollection;
    }

    @PostConstruct
    public void logConnectionProperties() {
        log.info("Connection nodes: {}, database: {}, collections: {},{},{}",connectionNodes,
                databaseName,postsCollection,commentsCollection,usersCollection);
    }

    private CodecRegistry getCodecRegistry() {
        CodecRegistry pojoCodecRegistry =
                fromRegistries(
                        MongoClientSettings.getDefaultCodecRegistry(),
                        fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        return pojoCodecRegistry;
    }

}
