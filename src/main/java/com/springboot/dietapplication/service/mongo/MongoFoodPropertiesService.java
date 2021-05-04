package com.springboot.dietapplication.service.mongo;

import com.springboot.dietapplication.model.mongo.properties.MongoFoodProperties;
import com.springboot.dietapplication.model.type.FoodPropertiesType;
import com.springboot.dietapplication.model.type.ProductType;
import com.springboot.dietapplication.repository.mongo.MongoFoodPropertiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MongoFoodPropertiesService {

    private final MongoFoodPropertiesRepository foodPropertiesRepository;

    @Autowired
    public MongoFoodPropertiesService(MongoFoodPropertiesRepository foodPropertiesRepository) {
        this.foodPropertiesRepository = foodPropertiesRepository;
    }

    public FoodPropertiesType findById(String foodPropertiesId) {
        Optional<MongoFoodProperties> foodProperties = this.foodPropertiesRepository.findById(foodPropertiesId);
        return foodProperties.map(FoodPropertiesType::new).orElseGet(FoodPropertiesType::new);
    }

    public void insertFoodProperties(FoodPropertiesType foodPropertiesType) {
        MongoFoodProperties foodProperties = new MongoFoodProperties(foodPropertiesType);
        this.foodPropertiesRepository.save(foodProperties);
        foodPropertiesType.setId(foodProperties.getId());
    }

    public void delete(String id) {
        this.foodPropertiesRepository.deleteById(id);
    }

}
