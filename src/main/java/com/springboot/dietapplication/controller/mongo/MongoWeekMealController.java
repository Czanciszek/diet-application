package com.springboot.dietapplication.controller.mongo;

import com.springboot.dietapplication.model.mongo.menu.MongoWeekMeal;
import com.springboot.dietapplication.service.mongo.MongoWeekMealService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/mongo/weekmeals")
public class MongoWeekMealController {

    private final MongoWeekMealService weekMealService;

    public MongoWeekMealController(MongoWeekMealService weekMealService) {
        this.weekMealService = weekMealService;
    }

    @GetMapping
    public List<MongoWeekMeal> getAll() {
        return this.weekMealService.getAll();
    }

    @GetMapping(path = "/{weekMealId}")
    public MongoWeekMeal getWeekMealById(@PathVariable("weekMealId") String weekMealId) {
        return this.weekMealService.getWeekMealById(weekMealId);
    }

    @PostMapping(produces = "application/json")
    ResponseEntity<MongoWeekMeal> insertWeekMeal(@RequestBody MongoWeekMeal weekMeal) {
        this.weekMealService.insert(weekMeal);
        return ResponseEntity.ok().body(weekMeal);
    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity<MongoWeekMeal> deleteById(@PathVariable String id) {
        this.weekMealService.delete(id);
        return ResponseEntity.ok().build();
    }
}
