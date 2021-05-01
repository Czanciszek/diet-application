package com.springboot.dietapplication.repository.mongo;

import com.springboot.dietapplication.model.dish.Dish;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DishRepository extends MongoRepository<Dish, String> {

}
