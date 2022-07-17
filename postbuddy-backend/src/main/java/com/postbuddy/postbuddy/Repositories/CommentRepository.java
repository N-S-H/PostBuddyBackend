package com.postbuddy.postbuddy.Repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.postbuddy.postbuddy.Exceptions.MongoException;
import com.postbuddy.postbuddy.Models.Entities.Comment;
import com.postbuddy.postbuddy.Models.Entities.Post;
import com.postbuddy.postbuddy.Models.Requests.CommentRequest;
import com.postbuddy.postbuddy.Models.Responses.GenericResponse;
import com.postbuddy.postbuddy.Utilities.PostBuddyConstants;
import lombok.extern.slf4j.Slf4j;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public GenericResponse getCommentsForPost(Post existingPost, int offsetValue) throws MongoException {
        Bson postIdFilter = Filters.eq("post_id",existingPost.getId());
        Bson sortOrder = Sorts.ascending("created_date");
        try {
            MongoCursor<Comment> commentMongoCursor = commentsCollection.find(postIdFilter)
                    .sort(sortOrder)
                    .skip(offsetValue).
                    limit(PostBuddyConstants.DEFAULT_LIMIT)
                    .iterator();
            List<Comment> commentList = new ArrayList<>();
            while(commentMongoCursor.hasNext()) commentList.add(commentMongoCursor.next());
            GenericResponse genericResponse = GenericResponse.builder().comments(commentList)
                    .limit(PostBuddyConstants.DEFAULT_LIMIT).offset(offsetValue).totalResults(existingPost.getNumOfComments()).build();
            return genericResponse;
        } catch (Exception e) {
            log.error("Exception {} occurred while fetching comments for a post",e.getMessage());
            throw new MongoException();
        }
    }
}
