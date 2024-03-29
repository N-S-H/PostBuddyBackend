package com.postbuddy.postbuddy.Exceptions.Handler;

import com.postbuddy.postbuddy.Exceptions.*;
import com.postbuddy.postbuddy.Models.Responses.GenericResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ControllerAdvice
@Slf4j
public class CommonExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ResponseEntity<GenericResponse> invalidUserException(InvalidUserException e) {
        log.debug("Invalid user exception occurred  because of the reason: "+e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(GenericResponse.builder().hasErrors(true).errorMessages(List.of(e.getMessage())).build());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ResponseEntity<GenericResponse> invalidPostException(InvalidPostException e) {
        log.debug("Invalid post exception occurred  because of the reason: "+e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(GenericResponse.builder().hasErrors(true).errorMessages(List.of(e.getMessage())).build());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ResponseEntity<GenericResponse> invalidCommentException(InvalidCommentException e) {
        log.debug("Invalid comment exception occurred  because of the reason: "+e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(GenericResponse.builder().hasErrors(true).errorMessages(List.of(e.getMessage())).build());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ResponseEntity<GenericResponse> invalidCommentFetchRequestException(InvalidCommentFetchRequestException e) {
        log.debug("Invalid comment fetch request exception occurred  because of the reason: "+e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(GenericResponse.builder().hasErrors(true).errorMessages(List.of(e.getMessage())).build());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ResponseEntity<GenericResponse> invalidPostFetchRequestException(InvalidPostFetchRequestException e) {
        log.debug("Invalid post fetch request exception occurred  because of the reason: "+e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(GenericResponse.builder().hasErrors(true).errorMessages(List.of(e.getMessage())).build());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ResponseEntity<GenericResponse> mongoException(MongoException e) {
        log.debug("Invalid user exception occurred  because of the reason: "+e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(GenericResponse.builder().hasErrors(true).errorMessages(List.of("Error occurred while communicating with the data source")).build());
    }

}
