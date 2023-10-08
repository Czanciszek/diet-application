package com.springboot.dietapplication.repository;

import com.springboot.dietapplication.model.psql.menu.PsqlProductMeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import java.util.List;

@Repository
public interface ProductMealRepository extends JpaRepository<PsqlProductMeal, Long> {

    List<PsqlProductMeal> findPsqlProductMealsByMealId(Long mealId);

    List<PsqlProductMeal> findPsqlProductDishesByProductId(Long productId);

    String selectProductsMealsQuery =
            "SELECT * FROM products_meals pm " +
                    "JOIN meals m ON m.id = pm.meal_id " +
                    "JOIN day_meals dm ON m.day_meal_id = dm.id " +
                    "JOIN week_meals wm ON dm.week_meal_id = wm.id " +
                    "JOIN menus me ON wm.menu_id = me.id  ";

    String whereMenuIdAndProductId = "WHERE me.id = :menuId AND pm.product_id = :productId";

    @Query(value = selectProductsMealsQuery + whereMenuIdAndProductId, nativeQuery = true)
    List<PsqlProductMeal> findPsqlProductMealByMenuIdAndByProductId(@Param("menuId") Long menuId, @Param("productId") Long productId);

    @Transactional
    void deletePsqlProductMealsByMealId(Long mealId);
}
