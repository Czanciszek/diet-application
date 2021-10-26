package com.springboot.dietapplication.controller;

import com.springboot.dietapplication.model.psql.user.PsqlUser;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/login")
    public String login() {
        return "Authenticated succesfully";
    }

    @PostMapping("/register")
    public PsqlUser register(@RequestBody PsqlUser user) {

        return user;
    }
}