package com.springboot.dietapplication.repository;

import com.springboot.dietapplication.model.psql.product.PsqlProductFoodProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ProductFoodPropertiesRepository extends JpaRepository<PsqlProductFoodProperties, Long> {

    String selectProductQuery = "SELECT " +
            "p.id, category, subcategory, name, lactose, starch, gluten, energy_value, proteins, fats, " +
            "saturated_fatty_acids, mono_unsaturated_fatty_acids, poly_unsaturated_fatty_acids, " +
            "cholesterol, carbohydrates, sucrose, dietary_fibres, sodium, potassium, calcium, phosphorus, magnesium, " +
            "iron, selenium, beta_carotene, vitamin_d, vitamin_c " +
            "FROM products p " +
            "JOIN food_properties fp ON p.food_properties_id = fp.id " +
            "JOIN categories c ON p.category_id = c.id ";

    @Query(value = selectProductQuery, nativeQuery = true)
    List<PsqlProductFoodProperties> getAllProducts();

    @Query(value = selectProductQuery +
            "WHERE p.id IN :productIdList", nativeQuery = true)
    List<PsqlProductFoodProperties> findProductsByIdIn(@Param("productIdList") Set<Long> ids);

    @Query(value = selectProductQuery +
            "WHERE p.id = :productId", nativeQuery = true)
    Optional<PsqlProductFoodProperties> findByProductId(@Param("productId") Long productId);

    @Query(value = selectProductQuery +
            "WHERE category_id = :categoryId", nativeQuery = true)
    List<PsqlProductFoodProperties> findPsqlProductsByCategoryId(@Param("categoryId") Long categoryIdList);

    @Query(value = selectProductQuery +
            "WHERE category_id IN :categoryIdList", nativeQuery = true)
    List<PsqlProductFoodProperties> findPsqlProductsByCategoryIdIn(@Param("categoryIdList") Set<Long> categoryIdList);
}
