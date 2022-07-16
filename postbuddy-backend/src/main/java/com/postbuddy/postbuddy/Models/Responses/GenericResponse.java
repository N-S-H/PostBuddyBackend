package com.postbuddy.postbuddy.Models.Responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.postbuddy.postbuddy.Models.Entities.Comment;
import com.postbuddy.postbuddy.Models.Entities.Post;
import com.postbuddy.postbuddy.Models.Entities.User;
import lombok.Builder;

import java.util.List;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericResponse {
    public Integer limit;
    public Integer offset;
    public Integer totalResults;
    public List<Post> posts;
    public List<User> users;
    public List<Comment> comments;
    public boolean hasErrors;
    public List<String> errorMessages;
}
