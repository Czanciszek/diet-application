package com.springboot.dietapplication.repository.mongo;

import com.springboot.dietapplication.model.mongo.menu.MongoMeal;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MongoMealRepository extends MongoRepository<MongoMeal, String> {

    List<MongoMeal> findByDayMealId(String dayMealId);

}
