package com.springboot.dietapplication.repository;

import com.springboot.dietapplication.model.psql.dish.PsqlDishUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Deprecated(since = "0.1.0", forRemoval = true)
@Repository
public interface DishUsageRepository  extends JpaRepository<PsqlDishUsage, Long> {

    String selectProductsMealsQuery =
            "SELECT me.id AS menu_id, me.start_date, me.end_date, d.id as dish_id, d.name AS dish_name, COUNT(d.id) AS dish_usage " +
                    "FROM dishes d " +
                    "JOIN meals m ON m.base_dish_id = d.id " +
                    "JOIN day_meals dm ON m.day_meal_id = dm.id " +
                    "JOIN week_meals wm ON dm.week_meal_id = wm.id " +
                    "JOIN menus me ON wm.menu_id = me.id " +
                    "JOIN patients p ON me.patient_id = p.id ";

    String whereMenuIdAndProductId = "WHERE p.id = :patientId ";

    String groupResults = "GROUP BY me.id, d.id";

    @Query(value = selectProductsMealsQuery + whereMenuIdAndProductId + groupResults, nativeQuery = true)
    List<PsqlDishUsage> findPsqlDishUsageByPatientId(@Param("patientId") Long patientId);
}

