package com.springboot.dietapplication.controller;

import com.springboot.dietapplication.model.type.DishType;
import com.springboot.dietapplication.model.type.DishUsageType;
import com.springboot.dietapplication.service.DishServiceV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v2/dishes")
public class DishControllerV2 {

    @Autowired
    DishServiceV2 dishService;

    @GetMapping
    ResponseEntity<?> getAll() {
        List<DishType> dishList = this.dishService.getAll();
        return ResponseEntity.ok(dishList);
    }

    @GetMapping(path = "/patient-usage/{patientId}")
    ResponseEntity<?> dishUsage(@PathVariable String patientId) {
        List<DishUsageType> dishUsageTypes = this.dishService.getDishUsages(patientId);
        return ResponseEntity.ok(dishUsageTypes);
    }

    @PostMapping(produces = "application/json")
    ResponseEntity<?> insert(@RequestBody DishType dishType) {
        DishType dish = this.dishService.insert(dishType);
        return ResponseEntity.ok().body(dish);
    }

    @PutMapping(produces = "application/json")
    ResponseEntity<?> update(@RequestBody DishType dishType) {
        DishType dish = this.dishService.update(dishType);
        return ResponseEntity.ok().body(dish);
    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity<?> delete(@PathVariable String id) {
        this.dishService.delete(id);
        return ResponseEntity.ok().build();
    }
}
