package com.springboot.dietapplication.repository;

import com.springboot.dietapplication.model.psql.menu.PsqlMeal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PsqlMealRepository extends JpaRepository<PsqlMeal, Long> {

    List<PsqlMeal> findByDayMealId(Long dayMealId);

}
