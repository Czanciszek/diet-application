package com.springboot.dietapplication.controller;

import com.springboot.dietapplication.model.psql.user.PsqlUser;
import com.springboot.dietapplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Deprecated(since = "0.1.0", forRemoval = true)
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping
    public List<PsqlUser> findAll() {
        return userRepository.findAll();
    }

}
