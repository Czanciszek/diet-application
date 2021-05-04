package com.springboot.dietapplication.repository.mongo;

import com.springboot.dietapplication.model.mongo.product.MongoCategory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoCategoryRepository extends MongoRepository<MongoCategory, String> {

    MongoCategory getCategoryByCategoryAndSubcategory(String category, String subcategory);

}
