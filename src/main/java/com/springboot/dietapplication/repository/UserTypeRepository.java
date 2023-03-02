package com.springboot.dietapplication.repository;

import com.springboot.dietapplication.model.psql.user.PsqlUserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTypeRepository extends JpaRepository<PsqlUserType, Long> {

    PsqlUserType findByName(String name);
}