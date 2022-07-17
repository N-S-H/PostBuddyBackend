package com.postbuddy.postbuddy.Services.Impl;

import com.postbuddy.postbuddy.Exceptions.*;
import com.postbuddy.postbuddy.Models.Entities.Post;
import com.postbuddy.postbuddy.Models.Entities.User;
import com.postbuddy.postbuddy.Models.Requests.PostRequest;
import com.postbuddy.postbuddy.Models.Responses.GenericResponse;
import com.postbuddy.postbuddy.Repositories.CommentRepository;
import com.postbuddy.postbuddy.Repositories.PostRepository;
import com.postbuddy.postbuddy.Repositories.UserRepository;
import com.postbuddy.postbuddy.Services.PostService;
import com.postbuddy.postbuddy.Utilities.PostBuddyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import static com.postbuddy.postbuddy.Utilities.PostBuddyConstants.*;

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

    @Override
    public GenericResponse getPosts(String nickName, String sortFilter, String offset) throws MongoException, InvalidPostException, InvalidPostFetchRequestException {
        if(nickName!=null && !nickName.isEmpty()) {
            validateUserExistence(nickName);
        }
        validateSortFilter(sortFilter);
        int offsetValue = validatePostFetchOffset(offset);
        GenericResponse genericResponse = fetchPostsBasedOnFilters(nickName,sortFilter,offsetValue);
        return genericResponse;
    }

    private GenericResponse fetchPostsBasedOnFilters(String nickName, String sortFilter, int offset) throws MongoException {
        GenericResponse genericResponse=null;
        switch(sortFilter) {
            case NEWEST:
                genericResponse = postRepository.fetchLatestPosts(nickName,offset);
                break;
            case OLDEST:
                genericResponse = postRepository.fetchOldestPosts(nickName,offset);
                break;
            case HIGHEST_COMMENTS:
                genericResponse = postRepository.fetchPostsWithHighestComments(nickName,offset);
                break;
        }
        return genericResponse;
    }

    private int validatePostFetchOffset(String offsetString) throws InvalidPostFetchRequestException {
        try {
            Integer offset = Integer.parseInt(offsetString);
            return offset.intValue();
        } catch (NumberFormatException e) {
            throw new InvalidPostFetchRequestException(ErrorMessages.INVALID_OFFSET_VALUE);
        }
    }

    private void validateSortFilter(String sortFilter) throws InvalidPostFetchRequestException {
        if(!sortFilter.equals(NEWEST) && !sortFilter.equals(OLDEST) && !sortFilter.equals(HIGHEST_COMMENTS))
        throw new InvalidPostFetchRequestException(ErrorMessages.INVALID_SORT_FILTER);
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
