package com.postbuddy.postbuddy.Exceptions;

public class InvalidPostFetchRequestException extends Exception{

    public InvalidPostFetchRequestException() {
        super();
    }

    public InvalidPostFetchRequestException(String message) {
        super(message);
    }
}
