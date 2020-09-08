package com.springboot.dietapplication.controller;

import com.springboot.dietapplication.model.dish.Dish;
import com.springboot.dietapplication.repository.DishRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/dishes")
public class DishController {
    private DishRepository dishRepository;

    public DishController(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    @GetMapping
    public List<Dish> getAll() {
        return this.dishRepository.findAll();
    }

    @PostMapping(produces = "application/json")
    ResponseEntity<Dish> insertDish(@RequestBody Dish dish) throws NoSuchFieldException {
        dishRepository.save(dish);
        return ResponseEntity.ok().body(null);
    }

    @PutMapping(path = "/{dishId}", produces = "application/json")
    ResponseEntity<Dish> updateDish(@RequestBody Dish dish) {
        dishRepository.save(dish);
        return ResponseEntity.ok().body(dish);
    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity<Dish> deleteDish(@PathVariable String id) {
        dishRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
