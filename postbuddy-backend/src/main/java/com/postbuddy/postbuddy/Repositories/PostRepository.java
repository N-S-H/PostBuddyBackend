package com.postbuddy.postbuddy.Repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.postbuddy.postbuddy.Exceptions.MongoException;
import com.postbuddy.postbuddy.Models.Entities.Post;
import lombok.extern.slf4j.Slf4j;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PostRepository {

    @Autowired
    @Qualifier("postsCollection")
    MongoCollection<Post> postsCollection;

    public void createPost(Post post) throws MongoException {
        try {
            postsCollection.insertOne(post);
        }catch (Exception e) {
            log.error("Exception {} occurred while inserting the post",e.getMessage());
            throw new MongoException();
        }
    }

    public Post findById(String postId) throws MongoException {
        Bson idFilter = Filters.eq("_id",postId);
        try {
            Post post = postsCollection.find(idFilter).first();
            return post;
        } catch (Exception e) {
            log.error("Exception {} occurred while fetching the post by id",e.getMessage());
            throw new MongoException();
        }
    }

    public void incrementCommentCount(String postId) throws MongoException {
        Bson idFilter = Filters.eq("_id",postId);
        Bson incrementUpdate = Updates.inc("comment_count",1);
        try {
            postsCollection.updateOne(idFilter,incrementUpdate);
        } catch (Exception e) {
            log.error("Exception {} occurred while incrementing the comment count",e.getMessage());
            throw new MongoException();
        }
    }
}
