package com.springboot.dietapplication.MongoRepository;

import com.springboot.dietapplication.Model.User.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    User findByName(String name);
}
