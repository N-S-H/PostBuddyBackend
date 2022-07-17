package com.postbuddy.postbuddy.Services.Impl;

import com.postbuddy.postbuddy.Exceptions.ErrorMessages;
import com.postbuddy.postbuddy.Exceptions.InvalidCommentException;
import com.postbuddy.postbuddy.Exceptions.InvalidCommentFetchRequestException;
import com.postbuddy.postbuddy.Exceptions.MongoException;
import com.postbuddy.postbuddy.Models.Entities.Comment;
import com.postbuddy.postbuddy.Models.Entities.Post;
import com.postbuddy.postbuddy.Models.Entities.User;
import com.postbuddy.postbuddy.Models.Requests.CommentRequest;
import com.postbuddy.postbuddy.Models.Responses.GenericResponse;
import com.postbuddy.postbuddy.Repositories.CommentRepository;
import com.postbuddy.postbuddy.Repositories.PostRepository;
import com.postbuddy.postbuddy.Repositories.UserRepository;
import com.postbuddy.postbuddy.Services.CommentService;
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
        validateUserExistence(commentRequest.getUserNickName());
        validatePostExistence(commentRequest.getPostId());
        validateCommentContent(commentRequest);
        Comment comment = utils.normalizeCreateCommentRequest(commentRequest);
        postRepository.incrementCommentCount(commentRequest.getPostId());
        commentRepository.createComment(comment);
        return comment;
    }

    @Override
    public Comment deleteComment(CommentRequest commentRequest) throws MongoException, InvalidCommentException {
        validateUserExistence(commentRequest.getUserNickName());
        Post existingPost = validatePostExistence(commentRequest.getPostId());
        validateCommentExistenceAndMatchUserInfo(commentRequest,existingPost);
        postRepository.decrementCommentCount(commentRequest.getPostId());
        Comment deletedComment = commentRepository.deleteComment(commentRequest);
        return deletedComment;
    }

    @Override
    public GenericResponse getCommentsForPost(String postId, String offset) throws MongoException, InvalidCommentException, InvalidCommentFetchRequestException {
        Post existingPost = validatePostExistence(postId);
        int offsetValue = validateCommentsFetchOffset(offset);
        GenericResponse genericResponse = commentRepository.getCommentsForPost(existingPost, offsetValue);
        return genericResponse;
    }

    private int validateCommentsFetchOffset(String offsetString) throws InvalidCommentFetchRequestException {
        try {
            Integer offset = Integer.parseInt(offsetString);
            return offset.intValue();
        } catch (NumberFormatException e) {
           throw new InvalidCommentFetchRequestException(ErrorMessages.INVALID_OFFSET_VALUE);
        }
    }

    private void validateCommentExistenceAndMatchUserInfo(CommentRequest commentRequest,Post existingPost) throws InvalidCommentException, MongoException {
        if(commentRequest.getId()==null || commentRequest.getId().isEmpty())
            throw new InvalidCommentException(ErrorMessages.COMMENT_ID_EMPTY);
        Comment existingComment = commentRepository.findCommentById(commentRequest.getId());
        if(existingComment==null)
            throw new InvalidCommentException(ErrorMessages.COMMENT_ID_NOT_EXISTS);
        if(!existingComment.getUserNickName().equals(commentRequest.getUserNickName())
          && !existingPost.getUserNickName().equals(commentRequest.getUserNickName()))
            throw new InvalidCommentException(ErrorMessages.COMMENT_DELETE_PROHIBITED_NICK_NAME);
    }

    private void validateCommentContent(CommentRequest commentRequest) throws InvalidCommentException, MongoException {
        if(commentRequest.getComment()==null||commentRequest.getComment().isEmpty())
            throw new InvalidCommentException(ErrorMessages.COMMENT_CONTENT_EMPTY);
        if(commentRequest.getComment().length()>allowedCommentContentLength)
            throw new InvalidCommentException(String.format(ErrorMessages.COMMENT_CONTENT_TOO_LONG,allowedCommentContentLength));
    }

    private void validateUserExistence(String nickName) throws InvalidCommentException, MongoException {
        if(nickName==null || nickName.isEmpty())
            throw new InvalidCommentException(ErrorMessages.NO_NICKNAME);
        User existingUser = userRepository.findByNickName(nickName);
        if(existingUser==null) throw new InvalidCommentException(ErrorMessages.USER_NICKNAME_DOES_NOT_EXISTS);
    }

    private Post validatePostExistence(String postId) throws InvalidCommentException, MongoException {
        if(postId==null || postId.isEmpty())
            throw new InvalidCommentException(ErrorMessages.POST_ID_EMPTY);
        Post existingPost = postRepository.findById(postId);
        if(existingPost==null) throw new InvalidCommentException(ErrorMessages.POST_ID_NOT_EXISTS);
        return existingPost;
    }

}
