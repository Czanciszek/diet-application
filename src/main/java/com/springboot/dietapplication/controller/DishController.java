package com.springboot.dietapplication.controller;

import com.springboot.dietapplication.model.type.DishType;
import com.springboot.dietapplication.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/dishes")
public class DishController {

    @Autowired
    DishService dishService;

    @GetMapping
    public List<DishType> getAll() {
        return this.dishService.getAll();
    }

    @GetMapping(path = "/{dishId}")
    public DishType getDishById(@PathVariable("dishId") Long dishId) {
        return this.dishService.getDishById(dishId);
    }

    @PostMapping(produces = "application/json")
    ResponseEntity<DishType> insert(@RequestBody DishType dish) {
        this.dishService.insert(dish);
        return ResponseEntity.ok().body(dish);
    }

    @PutMapping(path = "/{dishId}", produces = "application/json")
    ResponseEntity<DishType> update(@RequestBody DishType dish) {
        this.dishService.insert(dish);
        return ResponseEntity.ok().body(dish);
    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id) {
        this.dishService.delete(id);
        return ResponseEntity.ok().build();
    }
}
