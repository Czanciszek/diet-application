package com.springboot.dietapplication.repository;

import com.springboot.dietapplication.model.psql.product.PsqlProductFoodProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductFoodPropertiesRepository extends JpaRepository<PsqlProductFoodProperties, Long> {

    String selectProductQuery = "SELECT " +
            "p.id, p.user_id, category, subcategory, name, lactose, starch, gluten, energy_value, proteins, fats, " +
            "saturated_fatty_acids, mono_unsaturated_fatty_acids, poly_unsaturated_fatty_acids, " +
            "cholesterol, carbohydrates, sucrose, dietary_fibres, sodium, potassium, calcium, phosphorus, magnesium, " +
            "iron, selenium, beta_carotene, vitamin_d, vitamin_c " +
            "FROM products p " +
            "JOIN food_properties fp ON p.food_properties_id = fp.id " +
            "JOIN categories c ON p.category_id = c.id ";

    String userRestrictQuery = "WHERE p.is_system IS TRUE OR p.user_id = :userId";

    @Query(value = selectProductQuery + userRestrictQuery, nativeQuery = true)
    List<PsqlProductFoodProperties> getAllUserProducts(@Param("userId") Long userId);

    @Query(value = selectProductQuery, nativeQuery = true)
    List<PsqlProductFoodProperties> getAllProducts();

}
