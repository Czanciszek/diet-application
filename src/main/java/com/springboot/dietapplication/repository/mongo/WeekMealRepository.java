package com.springboot.dietapplication.repository.mongo;

import com.springboot.dietapplication.model.menu.WeekMeal;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeekMealRepository extends MongoRepository<WeekMeal, String> {

}
