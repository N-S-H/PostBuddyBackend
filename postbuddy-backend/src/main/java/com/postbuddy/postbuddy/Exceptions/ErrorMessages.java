package com.postbuddy.postbuddy.Exceptions;

import org.springframework.stereotype.Component;

@Component
public class ErrorMessages {
    public static String USER_NICKNAME_ALREADY_EXISTS = "The user already exists with the given nickname";
    public static String USER_EMAIL_ALREADY_EXISTS = "The user already exists with the given email";
    public static String NO_NICKNAME = "The user nickname cannot be null";
    public static String NO_EMAIL = "The user email cannot be empty";
    public static String INVALID_PASSWORD = "The user password is invalid";
    public static String DUPLICATE_NICKNAME="The entered nickname is already taken";
    public static String PASSWORDS_DID_NOT_MATCH = "The entered user password does not match with the existing password";
    public static String NO_FIRSTNAME = "The first name of the user cannot be empty";
}
