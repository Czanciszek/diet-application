package com.springboot.dietapplication.repository;

import com.springboot.dietapplication.model.product.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    Product findByNameLike(String name);

}