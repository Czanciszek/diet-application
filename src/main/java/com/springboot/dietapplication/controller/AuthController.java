package com.springboot.dietapplication.controller;

import com.springboot.dietapplication.model.user.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/login")
    public String login() {
        return "Authenticated succesfully";
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {

        return user;
    }
}