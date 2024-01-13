package com.springboot.dietapplication.repository;

import com.springboot.dietapplication.model.psql.menu.PsqlWeekMeal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Deprecated(since = "0.1.0", forRemoval = true)
public interface WeekMealRepository extends JpaRepository<PsqlWeekMeal, Long> {

    List<PsqlWeekMeal> getPsqlWeekMealsByMenuId(Long menuId);

}
