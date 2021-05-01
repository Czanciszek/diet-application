package com.springboot.dietapplication.repository.mongo;

import com.springboot.dietapplication.model.menu.Meal;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MealRepository extends MongoRepository<Meal, String> {

    List<Meal> findByDayMealIdLike(String dayMealId);

}
