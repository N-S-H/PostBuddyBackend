package com.postbuddy.postbuddy.Services;

import com.postbuddy.postbuddy.Models.Entities.User;
import com.postbuddy.postbuddy.Exceptions.InvalidUserException;
import com.postbuddy.postbuddy.Exceptions.MongoException;
import com.postbuddy.postbuddy.Models.Requests.UserRequest;
import org.springframework.stereotype.Component;

@Component
public interface UserService {
    User createUser(UserRequest userRequest) throws InvalidUserException, MongoException;

    User getUser(UserRequest userRequest) throws MongoException, InvalidUserException;
}
