package com.springboot.dietapplication.MongoRepository;

import com.springboot.dietapplication.Model.Dish.Dish;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DishRepository extends MongoRepository<Dish, String> {

}
