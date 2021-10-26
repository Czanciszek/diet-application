package com.springboot.dietapplication.controller;

import com.springboot.dietapplication.model.type.WeekMealType;
import com.springboot.dietapplication.service.WeekMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/weekmeals")
public class WeekMealController {

    @Autowired
    WeekMealService weekMealService;

    @GetMapping
    public List<WeekMealType> getAll() {
        return this.weekMealService.getAll();
    }

    @GetMapping(path = "/{weekMealId}")
    public WeekMealType getWeekMealById(@PathVariable("weekMealId") Long weekMealId) {
        return this.weekMealService.getWeekMealById(weekMealId);
    }

    @PostMapping(produces = "application/json")
    ResponseEntity<WeekMealType> insertWeekMeal(@RequestBody WeekMealType weekMeal) {
        this.weekMealService.insert(weekMeal);
        return ResponseEntity.ok().body(weekMeal);
    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity<WeekMealType> deleteById(@PathVariable Long id) {
        this.weekMealService.delete(id);
        return ResponseEntity.ok().build();
    }
}
