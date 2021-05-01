package com.springboot.dietapplication.repository.mongo;

import com.springboot.dietapplication.model.product.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface MongoProductRepository extends MongoRepository<Product, String> {

    List<Product> findByNameLike(String name);

    List<Product> findByCategoryLike(String category);

    List<Product> findBySubcategoryLike(String subcategory);

    List<Product> findByCategoryLikeAndSubcategoryLike(String category, String subcategory);

    List<Product> findProductsByIdIn(Set<String> productIdList);
}