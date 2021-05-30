package com.springboot.dietapplication.controller.mongo;

import com.springboot.dietapplication.model.type.MealType;
import com.springboot.dietapplication.service.mongo.MongoDayMealService;
import com.springboot.dietapplication.service.mongo.MongoMealService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/mongo/meals")
public class MongoMealController {

    private final MongoMealService mealService;
    private final MongoDayMealService dayMealService;

    public MongoMealController(MongoMealService mealService, MongoDayMealService dayMealService) {
        this.mealService = mealService;
        this.dayMealService = dayMealService;
    }

    @GetMapping
    public List<MealType> getAll() {
        return this.mealService.getAll();
    }

    @GetMapping(path = "/{dayMealId}")
    public MealType getMealById(@PathVariable("dayMealId") String dayMealId) {
        return this.mealService.getMealById(dayMealId);
    }

    @GetMapping(path = "/list/{dayMealIdList}")
    public List<MealType> getMealsByDayMealList(@PathVariable("dayMealIdList") List<String> dayMealIdList) {
        return this.mealService.getMealsByDayMealList(dayMealIdList);
    }

    @PostMapping(produces = "application/json")
    ResponseEntity<MealType> insertMeal(@RequestBody MealType meal) {
        this.mealService.insert(meal);
        this.dayMealService.addMealToDayMealResources(meal);
        return ResponseEntity.ok().body(meal);
    }

    @PostMapping(path="/copy", produces = "application/json")
    ResponseEntity<MealType> copyMeal(@RequestBody MealType meal) {
        MealType newMeal = new MealType(meal);
        this.mealService.insert(newMeal);
        this.dayMealService.addMealToDayMealResources(newMeal);
        return ResponseEntity.ok().body(newMeal);
    }

    @PutMapping(path = "/{mealId}", produces = "application/json")
    ResponseEntity<MealType> update(@RequestBody MealType meal) {
        this.mealService.insert(meal);
        return ResponseEntity.ok().body(meal);
    }

    @DeleteMapping(path = "/{mealId}")
    ResponseEntity<MealType> deleteMeal(@PathVariable String mealId) {
        MealType mealType = this.mealService.getMealById(mealId);
        this.dayMealService.deleteMealFromDayMealResources(mealType);
        this.mealService.delete(mealId);
        return ResponseEntity.ok().build();
    }
}
