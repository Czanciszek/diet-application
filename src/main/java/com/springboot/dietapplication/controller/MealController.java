package com.springboot.dietapplication.controller;

import com.springboot.dietapplication.model.menu.Meal;
import com.springboot.dietapplication.repository.MealRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/meals")
public class MealController {
    private MealRepository mealRepository;

    public MealController(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    @GetMapping
    public List<Meal> getAll() {
        return this.mealRepository.findAll();
    }

    @PostMapping(produces = "application/json")
    ResponseEntity<Meal> insertMeal(@RequestBody Meal meal) throws NoSuchFieldException {
        mealRepository.save(meal);
        return ResponseEntity.ok().body(null);
    }

    @PutMapping(path = "/{mealId}", produces = "application/json")
    ResponseEntity<Meal> updateMeal(@RequestBody Meal meal) {
        mealRepository.save(meal);
        return ResponseEntity.ok().body(meal);
    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity<Meal> deleteMeal(@PathVariable String id) {
        mealRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
