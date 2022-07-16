package com.postbuddy.postbuddy.Services;

import com.postbuddy.postbuddy.Exceptions.InvalidPostException;
import com.postbuddy.postbuddy.Exceptions.MongoException;
import com.postbuddy.postbuddy.Models.Entities.Post;
import com.postbuddy.postbuddy.Models.Requests.PostRequest;
import org.springframework.stereotype.Component;

@Component
public interface PostService {

    Post createPost(PostRequest postRequest) throws MongoException, InvalidPostException;

    Post updatePost(PostRequest postRequest) throws MongoException,InvalidPostException;

    Post deletePost(PostRequest postRequest) throws MongoException,InvalidPostException;
}
