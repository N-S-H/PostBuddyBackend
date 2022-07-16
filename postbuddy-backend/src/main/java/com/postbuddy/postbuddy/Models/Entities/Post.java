package com.postbuddy.postbuddy.Models.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Post {
    @BsonId
    @JsonProperty("_id")
    private String id;
    private String title;
    private String description;
    @BsonProperty("user_nick_name")
    private String userNickName;
    @BsonProperty("created_date")
    private String createdDate;
    @BsonProperty("last_modified_date")
    private String lastModifiedDate;
    @BsonProperty("comment_count")
    private int numOfComments;
}
