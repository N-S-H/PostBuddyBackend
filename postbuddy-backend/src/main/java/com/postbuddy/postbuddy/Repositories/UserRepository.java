package com.postbuddy.postbuddy.Repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.postbuddy.postbuddy.Models.Entities.User;
import com.postbuddy.postbuddy.Exceptions.MongoException;
import lombok.extern.slf4j.Slf4j;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserRepository {

    @Autowired
    @Qualifier("usersCollection")
    private MongoCollection<User> usersCollection;

    public User findByNickName(String nickName) throws MongoException {
        Bson nickNameFilter = Filters.eq("nick_name",nickName);
        try {
            User userDocument = usersCollection.find(nickNameFilter).first();
            return userDocument;
        } catch (Exception e) {
            log.error("Exception {} occurred while trying to find the existence of nickname",e.getMessage());
            throw new MongoException();
        }
    }

    public User findByEmail(String email) throws MongoException {
        Bson emailFilter = Filters.eq("email",email);
        try {
            User userDocument = usersCollection.find(emailFilter).first();
            return userDocument;
        } catch (Exception e) {
            log.error("Exception {} occurred while trying to find the existence of email",e.getMessage());
            throw new MongoException();
        }
    }

    public void createUser(User user) throws MongoException {
        try {
            usersCollection.insertOne(user);
        } catch (Exception e) {
            log.error("Exception {} occurred while trying to create the new user",e.getMessage());
            throw new MongoException();
        }
    }
}
