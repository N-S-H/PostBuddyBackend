package com.postbuddy.postbuddy.Services.Impl;

import com.postbuddy.postbuddy.Exceptions.ErrorMessages;
import com.postbuddy.postbuddy.Exceptions.InvalidPostException;
import com.postbuddy.postbuddy.Exceptions.MongoException;
import com.postbuddy.postbuddy.Models.Entities.Post;
import com.postbuddy.postbuddy.Models.Entities.User;
import com.postbuddy.postbuddy.Models.Requests.PostRequest;
import com.postbuddy.postbuddy.Repositories.CommentRepository;
import com.postbuddy.postbuddy.Repositories.PostRepository;
import com.postbuddy.postbuddy.Repositories.UserRepository;
import com.postbuddy.postbuddy.Services.PostService;
import com.postbuddy.postbuddy.Utilities.PostBuddyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostBuddyUtils utils;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @Value("${post.allowedTitleLength}")
    private int allowedTitleLength;

    @Value("${post.allowedDescriptionLength}")
    private int allowedDescriptionLength;

    @Override
    public Post createPost(PostRequest postRequest) throws MongoException, InvalidPostException {
        validateUserExistence(postRequest.getUserNickName());
        validatePostContent(postRequest);
        Post post = utils.normalizeCreatePostRequest(postRequest);
        postRepository.createPost(post);
        return post;
    }

    @Override
    public Post updatePost(PostRequest postRequest) throws MongoException, InvalidPostException {
        validateUserExistence(postRequest.getUserNickName());
        validatePostContent(postRequest);
        validatePostExistenceAndMatchUserInfo(postRequest);
        String currentDateTime = utils.getCurrentDate();
        Post updatedPost = postRepository.updatePost(postRequest,currentDateTime);
        return updatedPost;
    }

    @Override
    public Post deletePost(PostRequest postRequest) throws MongoException, InvalidPostException {
        validateUserExistence(postRequest.getUserNickName());
        validatePostExistenceAndMatchUserInfo(postRequest);
        commentRepository.deleteCommentsFromPostId(postRequest.getId());
        Post deletedPost = postRepository.deletePost(postRequest);
        return deletedPost;
    }

    private void validatePostExistenceAndMatchUserInfo(PostRequest postRequest) throws MongoException, InvalidPostException {
            String postId = postRequest.getId();
            if(postId==null || postId.isEmpty())
                throw new InvalidPostException(ErrorMessages.POST_ID_EMPTY);
            Post existingPost = postRepository.findById(postId);
            if(existingPost==null) throw new InvalidPostException(ErrorMessages.POST_ID_NOT_EXISTS);
            if(!existingPost.getUserNickName().equals(postRequest.getUserNickName()))
                throw new InvalidPostException(ErrorMessages.USER_NICK_NAME_MISMATCH);
    }

    private void validateUserExistence(String nickName) throws InvalidPostException, MongoException {
        if(nickName==null || nickName.isEmpty())
            throw new InvalidPostException(ErrorMessages.NO_NICKNAME);
        User userByNickName = userRepository.findByNickName(nickName);
        if(userByNickName==null) throw new InvalidPostException(ErrorMessages.USER_NICKNAME_DOES_NOT_EXISTS);
    }

    private void validatePostContent(PostRequest postRequest) throws InvalidPostException {
        if(postRequest.getTitle()==null || postRequest.getTitle().isEmpty())
            throw new InvalidPostException(ErrorMessages.POST_TITLE_EMPTY);
        if(postRequest.getTitle().length()>allowedTitleLength)
            throw new InvalidPostException(String.format(ErrorMessages.POST_TITLE_TOO_LONG,allowedTitleLength));
        if(postRequest.getDescription().length()>allowedDescriptionLength)
            throw new InvalidPostException(String.format(ErrorMessages.POST_DESCRIPTION_TOO_LONG,allowedDescriptionLength));
    }
}
