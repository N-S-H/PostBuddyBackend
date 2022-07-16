package com.postbuddy.postbuddy.Services.Impl;

import com.postbuddy.postbuddy.Models.Entities.User;
import com.postbuddy.postbuddy.Exceptions.ErrorMessages;
import com.postbuddy.postbuddy.Exceptions.InvalidUserException;
import com.postbuddy.postbuddy.Exceptions.MongoException;
import com.postbuddy.postbuddy.Repositories.UserRepository;
import com.postbuddy.postbuddy.Models.Requests.UserRequest;
import com.postbuddy.postbuddy.Services.UserService;
import com.postbuddy.postbuddy.Utilities.PostBuddyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostBuddyUtils utils;

    @Override
    public User createUser(UserRequest userRequest) throws InvalidUserException, MongoException {
        validateUser(userRequest);
        User user = utils.normalizeCreateUserRequest(userRequest);
        userRepository.createUser(user);
        return user;
    }

    private void validateUser(UserRequest user) throws InvalidUserException, MongoException {
        if(user.getNickName()==null || user.getNickName().isEmpty())
            throw new InvalidUserException(ErrorMessages.NO_NICKNAME);
        if(user.getEmail()==null || user.getEmail().isEmpty())
            throw new InvalidUserException(ErrorMessages.NO_EMAIL);
        if(user.getPassword()==null || user.getPassword().isEmpty())
            throw new InvalidUserException(ErrorMessages.INVALID_PASSWORD);
        if(user.getFirstName()==null || user.getFirstName().isEmpty())
            throw new InvalidUserException(ErrorMessages.NO_FIRSTNAME);
        boolean existingUserNickName = userRepository.findByNickName(user.getNickName());
        if(existingUserNickName) throw new InvalidUserException(ErrorMessages.USER_NICKNAME_ALREADY_EXISTS);
        boolean existingUserEmail = userRepository.findByEmail(user.getEmail());
        if(existingUserEmail) throw new InvalidUserException(ErrorMessages.USER_EMAIL_ALREADY_EXISTS);
    }


}
