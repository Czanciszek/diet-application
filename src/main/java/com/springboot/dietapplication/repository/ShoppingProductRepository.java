package com.springboot.dietapplication.repository;

import com.springboot.dietapplication.model.psql.product.PsqlShoppingProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingProductRepository extends JpaRepository<PsqlShoppingProduct, Long> {

    String selectShoppingProductsQuery = "SELECT " +
            "c.category as category_name, p.id as product_id, p.name as product_name, SUM(pm.grams) as grams FROM products p " +
            "JOIN categories c on c.id = p.category_id " +
            "JOIN products_meals pm ON p.id = pm.product_id " +
            "JOIN meals m ON m.id = pm.meal_id " +
            "JOIN day_meals dm ON dm.id = m.day_meal_id " +
            "JOIN week_meals wm ON wm.id = dm.week_meal_id " +
            "JOIN menus me ON me.id = wm.menu_id ";

    String groupProductsQuery = "GROUP BY c.category, p.id ";
    String orderProductsQuery = "ORDER BY c.category, p.name ";

    @Query(value = selectShoppingProductsQuery +
            "WHERE me.id = :menuId "
            + groupProductsQuery
            + orderProductsQuery, nativeQuery = true)
    List<PsqlShoppingProduct> findShoppingProductsByMenuId(@Param("menuId") Long menuId);

}
