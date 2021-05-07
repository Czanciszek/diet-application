package com.springboot.dietapplication.repository.mongo;

import com.springboot.dietapplication.model.mongo.product.MongoCategory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MongoCategoryRepository extends MongoRepository<MongoCategory, String> {

    MongoCategory getCategoryByCategoryAndSubcategory(String category, String subcategory);

    List<MongoCategory> findMongoCategoriesByCategory(String category);

    MongoCategory findMongoCategoryBySubcategory(String subcategory);

}
