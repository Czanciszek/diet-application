package com.springboot.dietapplication.repository;

import com.springboot.dietapplication.model.psql.user.PsqlUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Deprecated(since = "0.1.0", forRemoval = true)
@Repository
public interface UserRepository extends JpaRepository<PsqlUser, Long> {

    PsqlUser findByName(String name);
}