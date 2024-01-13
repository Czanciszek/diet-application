package com.springboot.dietapplication.repository;

import com.springboot.dietapplication.model.psql.menu.PsqlDayMeal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Deprecated(since = "0.1.0", forRemoval = true)
public interface DayMealRepository extends JpaRepository<PsqlDayMeal, Long> {

    List<PsqlDayMeal> getPsqlDayMealsByWeekMealId(Long weekMealId);
}
