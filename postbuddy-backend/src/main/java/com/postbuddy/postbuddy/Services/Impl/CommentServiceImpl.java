package com.postbuddy.postbuddy.Services.Impl;

import com.postbuddy.postbuddy.Exceptions.ErrorMessages;
import com.postbuddy.postbuddy.Exceptions.InvalidCommentException;
import com.postbuddy.postbuddy.Exceptions.MongoException;
import com.postbuddy.postbuddy.Models.Entities.Comment;
import com.postbuddy.postbuddy.Models.Entities.Post;
import com.postbuddy.postbuddy.Models.Entities.User;
import com.postbuddy.postbuddy.Models.Requests.CommentRequest;
import com.postbuddy.postbuddy.Repositories.CommentRepository;
import com.postbuddy.postbuddy.Repositories.PostRepository;
import com.postbuddy.postbuddy.Repositories.UserRepository;
import com.postbuddy.postbuddy.Services.CommentService;
import com.postbuddy.postbuddy.Utilities.DbOperationTypes;
import com.postbuddy.postbuddy.Utilities.PostBuddyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostBuddyUtils utils;

    @Value("${comment.allowedContentLength}")
    private int allowedCommentContentLength;

    @Override
    public Comment createComment(CommentRequest commentRequest) throws MongoException, InvalidCommentException {
        validateComment(commentRequest, DbOperationTypes.CREATE);
        Comment comment = utils.normalizeCreateCommentRequest(commentRequest);
        postRepository.incrementCommentCount(commentRequest.getPostId());
        commentRepository.createComment(comment);
        return comment;
    }

    private void validateComment(CommentRequest commentRequest, DbOperationTypes type) throws InvalidCommentException, MongoException {
        if(commentRequest.getUserNickName()==null || commentRequest.getUserNickName().isEmpty())
            throw new InvalidCommentException(ErrorMessages.NO_NICKNAME);
        if(commentRequest.getComment()==null||commentRequest.getComment().isEmpty())
            throw new InvalidCommentException(ErrorMessages.COMMENT_CONTENT_EMPTY);
        if(commentRequest.getPostId()==null || commentRequest.getPostId().isEmpty())
            throw new InvalidCommentException(ErrorMessages.POST_ID_EMPTY);
        if(commentRequest.getComment().length()>allowedCommentContentLength)
            throw new InvalidCommentException(String.format(ErrorMessages.COMMENT_CONTENT_TOO_LONG,allowedCommentContentLength));
        User existingUser = userRepository.findByNickName(commentRequest.getUserNickName());
        if(existingUser==null) throw new InvalidCommentException(ErrorMessages.USER_NICKNAME_DOES_NOT_EXISTS);
        Post existingPost = postRepository.findById(commentRequest.getPostId());
        if(existingPost==null) throw new InvalidCommentException(ErrorMessages.POST_ID_NOT_EXISTS);

    }


}
