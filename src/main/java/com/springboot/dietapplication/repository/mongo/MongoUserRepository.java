package com.springboot.dietapplication.repository.mongo;

import com.springboot.dietapplication.model.mongo.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoUserRepository extends MongoRepository<User, String> {

    User findByName(String name);

}
