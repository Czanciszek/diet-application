package com.springboot.dietapplication.repository;

import com.springboot.dietapplication.model.psql.menu.PsqlMeal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Deprecated(since = "0.1.0", forRemoval = true)
public interface MealRepository extends JpaRepository<PsqlMeal, Long> {

    List<PsqlMeal> findByDayMealId(Long dayMealId);

    List<PsqlMeal> findByName(String name);

}
