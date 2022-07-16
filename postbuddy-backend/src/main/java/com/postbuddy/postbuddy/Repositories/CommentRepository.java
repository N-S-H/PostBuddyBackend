package com.postbuddy.postbuddy.Repositories;

import com.mongodb.client.MongoCollection;
import com.postbuddy.postbuddy.Exceptions.MongoException;
import com.postbuddy.postbuddy.Models.Entities.Comment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CommentRepository {

    @Autowired
    @Qualifier("commentsCollection")
    MongoCollection<Comment> commentsCollection;


    public void createComment(Comment comment) throws MongoException {
        try {
            commentsCollection.insertOne(comment);
        } catch (Exception e) {
            log.error("Exception {} occurred while inserting the comment",e.getMessage());
            throw new MongoException();
        }
    }
}
