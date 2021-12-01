package com.springboot.dietapplication.repository;

import com.springboot.dietapplication.model.psql.menu.PsqlMenuProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuProductsRepository extends JpaRepository<PsqlMenuProduct, Long> {

    String selectProductQuery =
            "SELECT " +
            "p.id product_id, p.name product_name, " +
            "m.is_product, m.name meal_name, m.id meal_id, " +
            "ft.id food_type_id, ft.name food_type_name, " +
            "pm.grams, pm.amount, " +
            "at.name amount_type, " +
            "dm.day_type, dm.date date, " +
            "wm.id week_meal_id, " +
            "me.id menu_id " +
            "FROM products p " +
            "JOIN products_meals pm ON p.id = pm.product_id " +
            "JOIN meals m ON m.id = pm.meal_id " +
            "JOIN food_types ft ON ft.id = m.food_type_id " +
            "JOIN amount_types at ON pm.amount_type_id = at.id " +
            "JOIN day_meals dm ON m.day_meal_id = dm.id " +
            "JOIN week_meals wm ON dm.week_meal_id = wm.id " +
            "JOIN menus me ON wm.menu_id = me.id  ";

    String whereMenuIdQueryOrdered = "WHERE me.id = :menuId";

    @Query(value = selectProductQuery + whereMenuIdQueryOrdered, nativeQuery = true)
    List<PsqlMenuProduct> findMenuProducts(@Param("menuId") Long menuId);

}
