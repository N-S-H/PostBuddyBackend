package com.postbuddy.postbuddy.Controllers;

import com.postbuddy.postbuddy.Exceptions.InvalidPostException;
import com.postbuddy.postbuddy.Exceptions.InvalidUserException;
import com.postbuddy.postbuddy.Exceptions.MongoException;
import com.postbuddy.postbuddy.Models.Entities.Post;
import com.postbuddy.postbuddy.Models.Entities.User;
import com.postbuddy.postbuddy.Models.Requests.PostRequest;
import com.postbuddy.postbuddy.Models.Requests.UserRequest;
import com.postbuddy.postbuddy.Models.Responses.GenericResponse;
import com.postbuddy.postbuddy.Services.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("postbuddy")
@Slf4j
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping(path = "/post",consumes = MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> createPost(@RequestBody PostRequest postRequest) throws MongoException, InvalidPostException {
        ResponseEntity<GenericResponse> responseEntity;
        try {
            Post response = postService.createPost(postRequest);
            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(GenericResponse.builder().totalResults(1).posts(List.of(response)).build());
            log.info("The post entry has been successfully created in the internal data source for user {}",postRequest.getUserNickName());
        } catch(MongoException e) {
            log.error("Exception occurred while communicating with underlying data source");
            throw e;
        } catch (InvalidPostException e) {
            log.error("Exception occurred because of invalid post");
            throw e;
        }
        return responseEntity;
    }
}
