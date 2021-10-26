package com.springboot.dietapplication.repository;

import com.springboot.dietapplication.model.psql.menu.PsqlProductMeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface PsqlProductMealRepository extends JpaRepository<PsqlProductMeal, Long> {

    List<PsqlProductMeal> findPsqlProductMealsByMealId(Long mealId);

    @Transactional
    void deletePsqlProductMealsByMealId(Long mealId);
}
