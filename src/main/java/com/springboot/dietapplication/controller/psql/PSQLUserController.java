package com.springboot.dietapplication.controller.psql;

import com.springboot.dietapplication.model.psql.user.User;
import com.springboot.dietapplication.repository.psql.PsqlUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v2/users")
public class PSQLUserController {

    @Autowired
    PsqlUserRepository PSQLUserRepository;

    @GetMapping
    public List<User> findAll() {
        return PSQLUserRepository.findAll();
    }

}
