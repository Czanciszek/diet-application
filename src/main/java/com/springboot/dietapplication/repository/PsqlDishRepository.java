package com.springboot.dietapplication.repository;

import com.springboot.dietapplication.model.psql.dish.PsqlDish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PsqlDishRepository extends JpaRepository<PsqlDish, Long> {

}
