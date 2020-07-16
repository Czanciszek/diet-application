package com.springboot.dietapplication.repository;

import com.springboot.dietapplication.model.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    User findByName(String name);

}
