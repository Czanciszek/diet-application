package com.springboot.dietapplication.repository;

import com.springboot.dietapplication.model.menu.Meal;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealRepository extends MongoRepository<Meal, String> {

}
