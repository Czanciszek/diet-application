package com.springboot.dietapplication.repository.mongo;

import com.springboot.dietapplication.model.menu.DayMeal;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DayMealRepository extends MongoRepository<DayMeal, String> {

}
