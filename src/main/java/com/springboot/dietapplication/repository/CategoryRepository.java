package com.springboot.dietapplication.repository;

import com.springboot.dietapplication.model.product.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category, String> {

}