package com.postbuddy.postbuddy.Controllers;

import com.postbuddy.postbuddy.Exceptions.InvalidPostException;
import com.postbuddy.postbuddy.Exceptions.InvalidPostFetchRequestException;
import com.postbuddy.postbuddy.Exceptions.InvalidUserException;
import com.postbuddy.postbuddy.Exceptions.MongoException;
import com.postbuddy.postbuddy.Models.Entities.Post;
import com.postbuddy.postbuddy.Models.Entities.User;
import com.postbuddy.postbuddy.Models.Requests.PostRequest;
import com.postbuddy.postbuddy.Models.Requests.UserRequest;
import com.postbuddy.postbuddy.Models.Responses.GenericResponse;
import com.postbuddy.postbuddy.Services.PostService;
import com.postbuddy.postbuddy.Utilities.PostBuddyConstants;
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

    @PutMapping(path="/post",consumes = MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> updatePost(@RequestBody PostRequest postRequest) throws MongoException,InvalidPostException {
        ResponseEntity<GenericResponse> responseEntity;
        try {
            Post response = postService.updatePost(postRequest);
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(GenericResponse.builder().totalResults(1).posts(List.of(response)).build());
            log.info("The post entry has been successfully updated in the internal data source for user {}",postRequest.getUserNickName());
        } catch (MongoException e) {
            log.error("Exception occurred while communicating with the underlying data source");
            throw e;
        } catch (InvalidPostException e) {
            log.error("Exception occurred because of invalid post");
            throw e;
        }
        return responseEntity;
    }

    @DeleteMapping(path="/post",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> deletePost(@RequestBody PostRequest postRequest) throws MongoException,InvalidPostException {
        ResponseEntity<GenericResponse> responseEntity;
        try {
            Post response = postService.deletePost(postRequest);
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(GenericResponse.builder().totalResults(1).posts(List.of(response)).build());
            log.info("The post entry has been successfully deleted from the internal data source for user {}",postRequest.getUserNickName());
        } catch (MongoException e) {
            log.error("Exception occurred while communicating with the underlying data source");
            throw e;
        } catch (InvalidPostException e) {
            log.error("Exception occurred because of invalid post");
            throw e;
        }
        return responseEntity;
    }

    @GetMapping(path="/post", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> getPosts(@RequestParam(name = "userNickName",required = false,defaultValue = "") String nickName,
                                                    @RequestParam(name = "sortBy", required = false, defaultValue = PostBuddyConstants.NEWEST) String sortFilter,
                                                    @RequestParam(name = "offset",required=false,defaultValue = "0") String offset) throws MongoException, InvalidPostException, InvalidPostFetchRequestException {
        try {
            GenericResponse genericResponse = postService.getPosts(nickName,sortFilter,offset);
            ResponseEntity<GenericResponse> responseEntity = ResponseEntity.status(HttpStatus.OK).body(genericResponse);
            log.info("The posts have been successfully fetched based on given filters");
            return responseEntity;
        } catch (MongoException e) {
            log.error("Exception occurred while communicating with the underlying data source");
            throw e;
        } catch (InvalidPostException e) {
            log.error("Exception occurred because of invalid post");
            throw e;
        } catch (InvalidPostFetchRequestException e) {
            log.error("Exception occurred because of invalid pos request");
            throw e;
        }
    }
}
