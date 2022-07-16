package com.postbuddy.postbuddy.Exceptions;

public class InvalidPostException extends Exception{

    public InvalidPostException(){
        super();
    }

    public InvalidPostException(String message) {
        super(message);
    }
}
