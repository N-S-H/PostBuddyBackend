package com.postbuddy.postbuddy.Repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.postbuddy.postbuddy.Exceptions.MongoException;
import com.postbuddy.postbuddy.Models.Entities.Comment;
import com.postbuddy.postbuddy.Models.Requests.CommentRequest;
import lombok.extern.slf4j.Slf4j;
import org.bson.conversions.Bson;
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

    public void deleteCommentsFromPostId(String postId) throws MongoException {
        Bson postIdFilter = Filters.eq("post_id",postId);
        try {
            commentsCollection.deleteMany(postIdFilter);
        } catch (Exception e) {
            log.error("Exception {} occurred while deleting the comments based on post id",e.getMessage());
            throw new MongoException();
        }
    }

    public Comment findCommentById(String id) throws MongoException {
        Bson idFilter = Filters.eq("_id",id);
        Comment comment=null;
        try {
            comment = commentsCollection.find(idFilter).first();
            return comment;
        } catch (Exception e) {
            log.error("Exception {} occurred while fetching the comment based on comment id",e.getMessage());
            throw new MongoException();
        }
    }

    public Comment deleteComment(CommentRequest commentRequest) throws MongoException {
        Bson idFilter = Filters.eq("_id",commentRequest.getId());
        Comment deletedComment = null;
        try {
            deletedComment = commentsCollection.findOneAndDelete(idFilter);
            return deletedComment;
        } catch (Exception e) {
            log.error("Exception {} occurred while deleting the comment based on comment id",e.getMessage());
            throw new MongoException();
        }
    }
}
