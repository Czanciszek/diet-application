package com.springboot.dietapplication.controller;

import com.springboot.dietapplication.model.type.MealType;
import com.springboot.dietapplication.service.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/meals")
public class MealController {

    @Autowired
    MealService mealService;

    @GetMapping
    public List<MealType> getAll() {
        return this.mealService.getAll();
    }

    @GetMapping(path = "/{dayMealId}")
    public MealType getMealById(@PathVariable("dayMealId") Long dayMealId) {
        return this.mealService.getMealById(dayMealId);
    }

    @GetMapping(path = "/list/{weekMealId}")
    public List<MealType> getMealsByWeekMealId(@PathVariable("weekMealId") String weekMealId) {
        return this.mealService.getMealsByWeekMealId(weekMealId);
    }

    @PostMapping(produces = "application/json")
    ResponseEntity<MealType> insert(@RequestBody MealType meal) {
        this.mealService.insert(meal, false);
        return ResponseEntity.ok().body(meal);
    }

    @PostMapping(path="/copy", produces = "application/json")
    ResponseEntity<MealType> copy(@RequestBody MealType meal) {
        MealType newMeal = new MealType(meal);
        this.mealService.insert(newMeal, true);
        return ResponseEntity.ok().body(newMeal);
    }

    //TODO: Consider deleting mealId from path as looks unnecessary
    @PutMapping(path = "/{mealId}", produces = "application/json")
    ResponseEntity<MealType> update(@RequestBody MealType meal) {
        boolean isMealCopied = !meal.getOriginMealId().equals(Long.parseLong(meal.getId()));
        this.mealService.insert(meal, isMealCopied);
        return ResponseEntity.ok().body(meal);
    }

    @DeleteMapping(path = "/{mealId}")
    ResponseEntity<MealType> delete(@PathVariable Long mealId) {
        this.mealService.delete(mealId);
        return ResponseEntity.ok().build();
    }
}
