package com.postbuddy.postbuddy.Exceptions;

public class InvalidUserException extends Exception{

    public InvalidUserException() {
        super();
    }

    public InvalidUserException(String message) {
        super(message);
    }
}
