package com.springboot.dietapplication.controller.mongo;

import com.springboot.dietapplication.model.mongo.user.User;
import com.springboot.dietapplication.repository.mongo.MongoUserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class MongoUserController {

    private final MongoUserRepository mongoUserRepository;

    public MongoUserController(MongoUserRepository mongoUserRepository) {
        this.mongoUserRepository = mongoUserRepository;
    }

    @GetMapping
    public List<User> getAll() {
        return this.mongoUserRepository.findAll();
    }

}
