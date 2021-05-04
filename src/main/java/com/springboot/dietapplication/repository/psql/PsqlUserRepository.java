package com.springboot.dietapplication.repository.psql;

import com.springboot.dietapplication.model.psql.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PsqlUserRepository extends JpaRepository<User, Long> {

    List<User> findAll();

}