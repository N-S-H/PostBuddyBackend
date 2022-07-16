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
public class User {
    @BsonId
    @JsonProperty("_id")
    private String id;
    @BsonProperty("nick_name")
    private String nickName;
    private String email;
    private String password;
    @BsonProperty("first_name")
    private String firstName;
    @BsonProperty("last_name")
    private String lastName;

}
