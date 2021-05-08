package com.springboot.dietapplication.controller.psql;

import com.springboot.dietapplication.model.psql.user.PsqlUser;
import com.springboot.dietapplication.repository.psql.PsqlUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/psql/users")
public class PsqlUserController {

    @Autowired
    PsqlUserRepository userRepository;

    @GetMapping
    public List<PsqlUser> findAll() {
        return userRepository.findAll();
    }

}
