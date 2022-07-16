package com.postbuddy.postbuddy.Services.Impl;

import com.postbuddy.postbuddy.Exceptions.ErrorMessages;
import com.postbuddy.postbuddy.Exceptions.InvalidPostException;
import com.postbuddy.postbuddy.Exceptions.MongoException;
import com.postbuddy.postbuddy.Models.Entities.Post;
import com.postbuddy.postbuddy.Models.Entities.User;
import com.postbuddy.postbuddy.Models.Requests.PostRequest;
import com.postbuddy.postbuddy.Repositories.PostRepository;
import com.postbuddy.postbuddy.Repositories.UserRepository;
import com.postbuddy.postbuddy.Services.PostService;
import com.postbuddy.postbuddy.Utilities.DbOperationTypes;
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

    @Value("${post.allowedTitleLength}")
    private int allowedTitleLength;

    @Value("${post.allowedDescriptionLength}")
    private int allowedDescriptionLength;

    @Override
    public Post createPost(PostRequest postRequest) throws MongoException, InvalidPostException {
        validatePost(postRequest, DbOperationTypes.CREATE);
        Post post = utils.normalizeCreatePostRequest(postRequest);
        postRepository.createPost(post);
        return post;
    }

    private void validatePost(PostRequest postRequest,DbOperationTypes type) throws MongoException, InvalidPostException {
        if(postRequest.getUserNickName()==null || postRequest.getUserNickName().isEmpty())
            throw new InvalidPostException(ErrorMessages.NO_NICKNAME);
        if(postRequest.getTitle()==null || postRequest.getTitle().isEmpty())
            throw new InvalidPostException(ErrorMessages.POST_TITLE_EMPTY);
        User userByNickName = userRepository.findByNickName(postRequest.getUserNickName());
        if(userByNickName==null) throw new InvalidPostException(ErrorMessages.USER_NICKNAME_DOES_NOT_EXISTS);
        if(postRequest.getTitle().length()>allowedTitleLength)
            throw new InvalidPostException(String.format(ErrorMessages.POST_TITLE_TOO_LONG,allowedTitleLength));
        if(postRequest.getDescription().length()>allowedDescriptionLength)
            throw new InvalidPostException(String.format(ErrorMessages.POST_DESCRIPTION_TOO_LONG,allowedDescriptionLength));
    }
}
