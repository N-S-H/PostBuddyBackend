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

    public boolean findByNickName(String nickName) throws MongoException {
        Bson nickNameFilter = Filters.eq("nick_name",nickName);
        try {
            User userDocument = usersCollection.find(nickNameFilter).first();
            if(userDocument == null) return false;
        } catch (Exception e) {
            throw new MongoException();
        }
        return true;
    }

    public boolean findByEmail(String email) throws MongoException {
        Bson emailFilter = Filters.eq("email",email);
        try {
            User userDocument = usersCollection.find(emailFilter).first();
            if (userDocument == null) return false;
        } catch (Exception e) {
            throw new MongoException();
        }
        return true;
    }

    public void createUser(User user) throws MongoException {
        try {
            usersCollection.insertOne(user);
        } catch (Exception e) {
            throw new MongoException();
        }
    }
}
