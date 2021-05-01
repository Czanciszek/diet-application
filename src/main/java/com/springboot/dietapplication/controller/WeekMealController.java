package com.springboot.dietapplication.controller;

import com.springboot.dietapplication.model.menu.WeekMeal;
import com.springboot.dietapplication.repository.mongo.WeekMealRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/weekmeals")
public class WeekMealController {
    private final WeekMealRepository weekMealRepository;

    public WeekMealController(WeekMealRepository weekMealRepository) {
        this.weekMealRepository = weekMealRepository;
    }

    @GetMapping
    public List<WeekMeal> getAll() {
        return this.weekMealRepository.findAll();
    }

    @GetMapping(path = "/{weekMealId}")
    public Optional<WeekMeal> getWeekMealById(@PathVariable("weekMealId") String weekMealId) {
        return this.weekMealRepository.findById(weekMealId);
    }

    @PostMapping(produces = "application/json")
    ResponseEntity<WeekMeal> insertWeekMeal(@RequestBody WeekMeal weekMeal) throws NoSuchFieldException {
        weekMealRepository.save(weekMeal);
        return ResponseEntity.ok().body(null);
    }

    @PutMapping(path = "/{weekMealId}", produces = "application/json")
    ResponseEntity<WeekMeal> updateWeekMeal(@RequestBody WeekMeal weekMeal) {
        weekMealRepository.save(weekMeal);
        return ResponseEntity.ok().body(weekMeal);
    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity<WeekMeal> deleteWeekMeal(@PathVariable String id) {
        weekMealRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
