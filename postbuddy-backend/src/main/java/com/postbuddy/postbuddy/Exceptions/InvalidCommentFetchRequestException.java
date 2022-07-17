package com.postbuddy.postbuddy.Exceptions;

public class InvalidCommentFetchRequestException extends Exception{
    public InvalidCommentFetchRequestException() {
        super();
    }

    public InvalidCommentFetchRequestException(String message) {
        super(message);
    }
}
