package com.postbuddy.postbuddy.Utilities;

import com.postbuddy.postbuddy.Models.Entities.Comment;
import com.postbuddy.postbuddy.Models.Entities.Post;
import com.postbuddy.postbuddy.Models.Entities.User;
import com.postbuddy.postbuddy.Models.Requests.CommentRequest;
import com.postbuddy.postbuddy.Models.Requests.PostRequest;
import com.postbuddy.postbuddy.Models.Requests.UserRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    private String getCurrentDate() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime localDateTime = LocalDateTime.now();
        String dateTimeString = dateTimeFormatter.format(localDateTime);
        return dateTimeString;
    }

    public Post normalizeCreatePostRequest(PostRequest postRequest) {
        Post post = new Post();
        String postId = randomUUIDGenerator();
        String currentDateTime = getCurrentDate();
        post.setId(postId);
        post.setTitle(postRequest.getTitle());
        post.setDescription(postRequest.getDescription());
        post.setUserNickName(postRequest.getUserNickName());
        post.setCreatedDate(currentDateTime);
        post.setLastModifiedDate(currentDateTime);
        return post;
    }

    public Comment normalizeCreateCommentRequest(CommentRequest commentRequest) {
        Comment comment = new Comment();
        String commentId = randomUUIDGenerator();
        String currentDateTime = getCurrentDate();
        comment.setId(commentId);
        comment.setComment(commentRequest.getComment());
        comment.setPostId(commentRequest.getPostId());
        comment.setCreatedDate(currentDateTime);
        comment.setUserNickName(commentRequest.getUserNickName());
        return comment;
    }
}
