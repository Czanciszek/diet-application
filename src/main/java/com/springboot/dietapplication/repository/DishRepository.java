package com.springboot.dietapplication.repository;

import com.springboot.dietapplication.model.dish.Dish;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DishRepository extends MongoRepository<Dish, String> {

}
