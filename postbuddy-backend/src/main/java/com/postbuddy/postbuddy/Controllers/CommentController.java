package com.postbuddy.postbuddy.Controllers;

import com.postbuddy.postbuddy.Exceptions.InvalidCommentException;
import com.postbuddy.postbuddy.Exceptions.InvalidPostException;
import com.postbuddy.postbuddy.Exceptions.MongoException;
import com.postbuddy.postbuddy.Models.Entities.Comment;
import com.postbuddy.postbuddy.Models.Entities.Post;
import com.postbuddy.postbuddy.Models.Requests.CommentRequest;
import com.postbuddy.postbuddy.Models.Requests.PostRequest;
import com.postbuddy.postbuddy.Models.Responses.GenericResponse;
import com.postbuddy.postbuddy.Services.CommentService;
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
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping(path = "/comment",consumes = MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> createComment(@RequestBody CommentRequest commentRequest) throws MongoException, InvalidCommentException {
        ResponseEntity<GenericResponse> responseEntity;
        try {
            Comment response = commentService.createComment(commentRequest);
            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(GenericResponse.builder().totalResults(1).comments(List.of(response)).build());
            log.info("The comment entry has been successfully created in the internal data source for user {}",commentRequest.getUserNickName());
        } catch(MongoException e) {
            log.error("Exception occurred while communicating with underlying data source");
            throw e;
        } catch (InvalidCommentException e) {
            log.error("Exception occurred because of invalid comment");
            throw e;
        }
        return responseEntity;
    }
}
