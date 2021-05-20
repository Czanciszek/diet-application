package com.springboot.dietapplication.service.mongo;

import com.springboot.dietapplication.model.mongo.menu.MongoWeekMeal;
import com.springboot.dietapplication.repository.mongo.MongoWeekMealRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MongoWeekMealService {

    private final MongoWeekMealRepository weekMealRepository;

    public MongoWeekMealService(MongoWeekMealRepository weekMealRepository) {
        this.weekMealRepository = weekMealRepository;
    }

    public List<MongoWeekMeal> getAll() {
        return this.weekMealRepository.findAll();
    }

    public MongoWeekMeal getWeekMealById(String weekMealId) {
        Optional<MongoWeekMeal> mongoWeekMeal = this.weekMealRepository.findById(weekMealId);
        return mongoWeekMeal.orElseGet(MongoWeekMeal::new);
    }

    public ResponseEntity<MongoWeekMeal> insert(MongoWeekMeal weekMeal) {
        weekMealRepository.save(weekMeal);
        return ResponseEntity.ok().body(null);
    }

    public ResponseEntity<MongoWeekMeal> delete(String id) {
        weekMealRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
