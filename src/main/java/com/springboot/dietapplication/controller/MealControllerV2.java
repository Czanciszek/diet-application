package com.springboot.dietapplication.controller;

import com.springboot.dietapplication.model.type.*;
import com.springboot.dietapplication.service.MealServiceV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v2/meals")
public class MealControllerV2 {

    @Autowired
    MealServiceV2 mealService;

    @PostMapping(produces = "application/json")
    ResponseEntity<MealType> insert(@RequestBody NewMealType newMealType) {
        MealType newMeal = mealService.insert(newMealType);
        return ResponseEntity.ok().body(newMeal);
    }

    @PostMapping(path="/copyDay", produces = "application/json")
    ResponseEntity<?> copyDay(@RequestBody CopyDayMealsType copyDayMealsType) {
        mealService.copyDay(copyDayMealsType);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path="/copyMeal", produces = "application/json")
    ResponseEntity<?> copyMeal(@RequestBody CopyMealType copyMealType) {
        MealType copiedMeal = mealService.copyMeal(copyMealType);
        return ResponseEntity.ok().body(copiedMeal);
    }

    @PutMapping(produces = "application/json")
    ResponseEntity<MealType> update(@RequestBody NewMealType newMealType) {
        MealType updatedMeal = mealService.update(newMealType);
        return ResponseEntity.ok().body(updatedMeal);
    }

    @PutMapping(path = "/clearDay", produces = "application/json")
    ResponseEntity<?> clearDay(@RequestBody DayMealIdentifier dayMealIdentifier) {
        mealService.clearDay(dayMealIdentifier);
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "/removeMeal", produces = "application/json")
    ResponseEntity<?> removeMeal(@RequestBody MealIdentifier mealIdentifier) {
        mealService.removeMeal(mealIdentifier);
        return ResponseEntity.ok().build();
    }

}
