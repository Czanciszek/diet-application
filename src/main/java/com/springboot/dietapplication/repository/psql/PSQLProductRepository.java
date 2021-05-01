package com.springboot.dietapplication.repository.psql;

import com.springboot.dietapplication.model.psql.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface PSQLProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT p.id, p.category_id, p.subcategory_id, p.food_properties_id, p.name, c.name as category, sc.name as subcategory, p.lactose, p.gluten, p.starch FROM products p JOIN categories c ON p.category_id = c.id JOIN subcategories sc ON p.subcategory_id = sc.id", nativeQuery = true)
    List<Product> findAllProducts();

    //List<Product> findByNameLike(String name);

    //List<Product> findByCategoryNameLike(String categoryName);

    //List<Product> findBySubcategoryNameLike(String subcategory);

    //List<Product> findByCategoryNameLikeAndSubcategoryNameLike(String categoryName, String subcategory);

    List<Product> findProductsByIdIn(Set<Long> productIdList);

}
