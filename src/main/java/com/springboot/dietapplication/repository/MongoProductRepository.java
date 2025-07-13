package com.springboot.dietapplication.repository;

import com.springboot.dietapplication.model.mongo.product.MongoProduct;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoProductRepository extends MongoRepository<MongoProduct, String> {

}
