package com.postbuddy.postbuddy.Models.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Comment {
    @BsonId
    @JsonProperty("_id")
    private String id;
    private String comment;
    @BsonProperty("user_id")
    private String userId;
    @BsonProperty("post_id")
    private String postId;
}
