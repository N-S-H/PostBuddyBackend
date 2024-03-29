package com.postbuddy.postbuddy.Services;

import com.postbuddy.postbuddy.Exceptions.InvalidCommentException;
import com.postbuddy.postbuddy.Exceptions.InvalidCommentFetchRequestException;
import com.postbuddy.postbuddy.Exceptions.MongoException;
import com.postbuddy.postbuddy.Models.Entities.Comment;
import com.postbuddy.postbuddy.Models.Requests.CommentRequest;
import com.postbuddy.postbuddy.Models.Responses.GenericResponse;
import org.springframework.stereotype.Component;

@Component
public interface CommentService {
    Comment createComment(CommentRequest commentRequest) throws MongoException, InvalidCommentException;

    Comment deleteComment(CommentRequest commentRequest) throws MongoException,InvalidCommentException;

    GenericResponse getCommentsForPost(String postId, String offset) throws MongoException, InvalidCommentException, InvalidCommentFetchRequestException;
}
