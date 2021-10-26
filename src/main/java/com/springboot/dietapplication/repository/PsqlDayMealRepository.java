package com.springboot.dietapplication.repository;

import com.springboot.dietapplication.model.psql.menu.PsqlDayMeal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PsqlDayMealRepository extends JpaRepository<PsqlDayMeal, Long> {

    List<PsqlDayMeal> getPsqlDayMealsByWeekMealId(Long weekMealId);
}
