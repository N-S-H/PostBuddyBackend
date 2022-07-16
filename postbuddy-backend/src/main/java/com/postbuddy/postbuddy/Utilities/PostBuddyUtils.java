package com.postbuddy.postbuddy.Utilities;

import com.postbuddy.postbuddy.Models.Entities.User;
import com.postbuddy.postbuddy.Models.Requests.UserRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PostBuddyUtils {
    public User normalizeCreateUserRequest(UserRequest userRequest) {
        User user = new User();
        String userId = randomUUIDGenerator();
        user.setId(userId);
        user.setEmail(userRequest.getEmail());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setPassword(userRequest.getPassword());
        user.setNickName(userRequest.getNickName());
        return user;
    }

    private String randomUUIDGenerator() {
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }
}
