package com.postbuddy.postbuddy.Repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.*;
import com.mongodb.client.result.UpdateResult;
import com.postbuddy.postbuddy.Exceptions.MongoException;
import com.postbuddy.postbuddy.Models.Entities.Post;
import com.postbuddy.postbuddy.Models.Requests.PostRequest;
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

    public Post  updatePost(PostRequest postRequest,String currentDateTime) throws MongoException {
        Bson idFilter = Filters.eq("_id",postRequest.getId());
        Bson updates = Updates.combine(Updates.set("title",postRequest.getTitle()),
                Updates.set("description",postRequest.getDescription()),
                Updates.set("last_modified_date",currentDateTime));
        Post post=null;
        try {
            post = postsCollection.findOneAndUpdate(idFilter,updates, new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER));
        } catch (Exception e) {
            log.error("Exception {} occurred while updating the existing post",e.getMessage());
            throw new MongoException();
        }
        return post;
    }

    public Post deletePost(PostRequest postRequest) throws MongoException{
        Bson idFilter = Filters.eq("_id",postRequest.getId());
        Post deletedPost=null;
        try {
            deletedPost = postsCollection.findOneAndDelete(idFilter);
        } catch (Exception e) {
            log.error("Exception {} occurred while deleting the post",e.getMessage());
            throw new MongoException();
        }
        return deletedPost;
    }

    public void decrementCommentCount(String postId) throws MongoException {
        Bson idFilter = Filters.eq("_id",postId);
        Bson decrementUpdate = Updates.inc("comment_count",-1);
        try {
            postsCollection.updateOne(idFilter,decrementUpdate);
        } catch (Exception e) {
            log.error("Exception {} occurred while decrementing the comment count",e.getMessage());
            throw new MongoException();
        }
    }
}
