package com.springboot.dietapplication.repository;

import com.springboot.dietapplication.model.psql.user.PsqlUserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Deprecated(since = "0.1.0", forRemoval = true)
@Repository
public interface UserTypeRepository extends JpaRepository<PsqlUserType, Long> {

    PsqlUserType findByName(String name);
}