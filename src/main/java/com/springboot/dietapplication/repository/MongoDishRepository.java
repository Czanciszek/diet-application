package com.springboot.dietapplication.repository;

import com.springboot.dietapplication.model.mongo.dish.MongoDish;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MongoDishRepository extends MongoRepository<MongoDish, String> {

    List<MongoDish> findByProductsProductId(String productId);
}
