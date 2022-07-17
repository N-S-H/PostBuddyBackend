package com.postbuddy.postbuddy.Controllers;

import com.postbuddy.postbuddy.Models.Entities.User;
import com.postbuddy.postbuddy.Exceptions.InvalidUserException;
import com.postbuddy.postbuddy.Exceptions.MongoException;
import com.postbuddy.postbuddy.Models.Requests.UserRequest;
import com.postbuddy.postbuddy.Models.Responses.GenericResponse;
import com.postbuddy.postbuddy.Services.UserService;
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
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(path = "/user",consumes = MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> createUser(@RequestBody UserRequest userRequest) throws MongoException, InvalidUserException {
        ResponseEntity<GenericResponse> responseEntity;
        try {
            User response = userService.createUser(userRequest);
            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(GenericResponse.builder().totalResults(1).users(List.of(response)).build());
            log.info("The user entry has been successfully created in the internal data source for {}",userRequest.getNickName());
        } catch(MongoException e) {
            log.error("Exception occurred while communicating with underlying data source");
            throw e;
        } catch (InvalidUserException e) {
            log.error("Exception occurred because of invalid user");
            throw e;
        }
        return responseEntity;
    }

    @GetMapping(path="/user",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> getUser(@RequestBody UserRequest userRequest) throws MongoException, InvalidUserException {
        ResponseEntity<GenericResponse> responseEntity;
        try {
            User response = userService.getUser(userRequest);
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(GenericResponse.builder().totalResults(1).users(List.of(response)).build());
        } catch(MongoException e) {
            log.error("Exception occurred while communicating with underlying data source");
            throw e;
        } catch (InvalidUserException e) {
            log.error("Exception occurred because of invalid user");
            throw e;
        }
        return responseEntity;
    }

}
