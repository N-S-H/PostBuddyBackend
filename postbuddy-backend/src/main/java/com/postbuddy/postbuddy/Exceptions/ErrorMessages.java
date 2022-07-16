package com.postbuddy.postbuddy.Exceptions;

import org.springframework.stereotype.Component;

@Component
public class ErrorMessages {
    public static String USER_NICKNAME_ALREADY_EXISTS = "The user already exists with the given nickname";
    public static String USER_EMAIL_ALREADY_EXISTS = "The user already exists with the given email";
    public static String NO_NICKNAME = "The user nickname cannot be empty";
    public static String NO_EMAIL = "The user email cannot be empty";
    public static String INVALID_PASSWORD = "The user password is invalid";
    public static String DUPLICATE_NICKNAME="The entered nickname is already taken";
    public static String PASSWORDS_DID_NOT_MATCH = "The entered user password does not match with the existing password";
    public static String NO_FIRSTNAME = "The first name of the user cannot be empty";

    public static String USER_NICKNAME_DOES_NOT_EXISTS = "The user does not exist with the given nickname";
    public static String POST_TITLE_EMPTY = "The post title cannot be empty";
    public static String POST_TITLE_TOO_LONG = "The post title exceeded maximum character limit %s";
    public static String POST_DESCRIPTION_TOO_LONG = "The post description exceeded maximum character limit %s";

    public static String COMMENT_CONTENT_EMPTY = "The comment content is empty";

    public static String POST_ID_EMPTY = "The unique identifier for post should not be empty";

    public static final String POST_ID_NOT_EXISTS = "The post with the mentioned identifier does not exist";
    public static final String COMMENT_CONTENT_TOO_LONG = "The comment content exceeded maximum character limit %s";

    public static final String USER_NICK_NAME_MISMATCH = "Mismatch between the user nick names occurred in the existing entry and current entry";
    public static final String COMMENT_ID_NOT_EXISTS = "The comment with the mentioned id does not exist";

    public static final String COMMENT_ID_EMPTY = "The unique identifier of the comment should not be empty";

    public static final String COMMENT_DELETE_PROHIBITED_NICK_NAME = "Only the post author or comment author can delete the comment";
}
