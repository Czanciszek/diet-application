package com.springboot.dietapplication.repository;

import com.springboot.dietapplication.model.psql.menu.PsqlWeekMeal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PsqlWeekMealRepository extends JpaRepository<PsqlWeekMeal, Long> {

    List<PsqlWeekMeal> getPsqlWeekMealsByMenuId(Long menuId);

}
