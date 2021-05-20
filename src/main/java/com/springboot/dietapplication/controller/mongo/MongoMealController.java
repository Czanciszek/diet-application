package com.springboot.dietapplication.controller.mongo;

import com.springboot.dietapplication.model.mongo.menu.MongoMeal;
import com.springboot.dietapplication.service.mongo.MongoMealService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/mongo/meals")
public class MongoMealController {

    private final MongoMealService mealService;

    public MongoMealController(MongoMealService mealService) {
        this.mealService = mealService;
    }

    @GetMapping
    public List<MongoMeal> getAll() {
        return this.mealService.getAllMeals();
    }

    @GetMapping(path = "/list/{dayMealIdList}")
    public List<MongoMeal> getMealsByDayMealList(@PathVariable("dayMealIdList") List<String> dayMealIdList) {
        return this.mealService.getMealsByDayMealList(dayMealIdList);
    }

    @PostMapping(produces = "application/json")
    ResponseEntity<MongoMeal> insertMeal(@RequestBody MongoMeal meal) {
        this.mealService.createMeal(meal, true);
        return ResponseEntity.ok().body(meal);
    }

    @PostMapping(path="/copy", produces = "application/json")
    ResponseEntity<MongoMeal> copyMeal(@RequestBody MongoMeal meal) {
        MongoMeal newMeal = new MongoMeal(meal);
        this.mealService.createMeal(newMeal, true);
        return ResponseEntity.ok().body(meal);
    }

    @PutMapping(path = "/{mealId}", produces = "application/json")
    ResponseEntity<MongoMeal> updateMeal(@RequestBody MongoMeal meal) {
        this.mealService.createMeal(meal, false);
        return ResponseEntity.ok().body(meal);
    }

    @DeleteMapping(path = "/{mealId}")
    ResponseEntity<MongoMeal> deleteMeal(@PathVariable String mealId) {
        Optional<MongoMeal> meal = this.mealService.getMealById(mealId);
        if (!meal.isPresent()) {
            return ResponseEntity.ok().build();
        }
        this.mealService.removeMeal(meal.get());
        return ResponseEntity.ok().build();
    }
}
