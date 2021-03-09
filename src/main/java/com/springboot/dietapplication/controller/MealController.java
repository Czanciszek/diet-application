package com.springboot.dietapplication.controller;

import com.springboot.dietapplication.model.menu.Meal;
import com.springboot.dietapplication.service.MealService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/v1/meals")
public class MealController {

    private final MealService mealService;

    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

    @GetMapping
    public List<Meal> getAll() {
        return this.mealService.getAllMeals();
    }

    @GetMapping(path = "/list/{dayMealIdList}")
    public List<Meal> getMealsByDayMealList(@PathVariable("dayMealIdList") List<String> dayMealIdList) {
        return this.mealService.getMealsByDayMealList(dayMealIdList);
    }

    @PostMapping(produces = "application/json")
    ResponseEntity<Meal> insertMeal(@RequestBody Meal meal) {
        this.mealService.createMeal(meal, true);
        return ResponseEntity.ok().body(meal);
    }

    @PostMapping(path="/copy", produces = "application/json")
    ResponseEntity<Meal> copyMeal(@RequestBody Meal meal) {
        Meal newMeal = new Meal(meal);
        this.mealService.createMeal(newMeal, true);
        return ResponseEntity.ok().body(meal);
    }

    @PutMapping(path = "/{mealId}", produces = "application/json")
    ResponseEntity<Meal> updateMeal(@RequestBody Meal meal) {
        this.mealService.createMeal(meal, false);
        return ResponseEntity.ok().body(meal);
    }

    @DeleteMapping(path = "/{mealId}")
    ResponseEntity<Meal> deleteMeal(@PathVariable String mealId) {
        Optional<Meal> meal = this.mealService.getMealById(mealId);
        if (!meal.isPresent()) {
            return ResponseEntity.ok().build();
        }
        this.mealService.removeMeal(meal.get());
        return ResponseEntity.ok().build();
    }
}
