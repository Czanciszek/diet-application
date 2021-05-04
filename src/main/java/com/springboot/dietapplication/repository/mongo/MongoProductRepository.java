package com.springboot.dietapplication.repository.mongo;

import com.springboot.dietapplication.model.mongo.product.MongoProduct;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface MongoProductRepository extends MongoRepository<MongoProduct, String> {

    List<MongoProduct> findByNameLike(String name);

    List<MongoProduct> findProductsByIdIn(Set<String> productIdList);
}