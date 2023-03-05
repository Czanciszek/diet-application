package com.springboot.dietapplication.controller;

import com.springboot.dietapplication.model.type.DishType;
import com.springboot.dietapplication.model.type.DishUsageType;
import com.springboot.dietapplication.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/v1/dishes")
public class DishController {

    @Autowired
    DishService dishService;

    @GetMapping
    ResponseEntity<?> getAll() {
        try {
            List<DishType> dishList = this.dishService.getAll();
            return ResponseEntity.ok(dishList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(path = "/patient-usage/{patientId}")
    ResponseEntity<?> dishUsage(@PathVariable Long patientId) {
        try {
            List<DishUsageType> dishUsages = dishService.getDishUsages(patientId);
            return ResponseEntity.ok(dishUsages);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(patientId);
        }
    }

    @PostMapping(produces = "application/json")
    ResponseEntity<?> insert(@RequestBody DishType dishType) {
        try {
            DishType dish = this.dishService.insert(dishType);
            return ResponseEntity.ok().body(dish);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<HttpStatus>(e.getStatus());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(dishType);
        }
    }

    @PutMapping(produces = "application/json")
    ResponseEntity<?> update(@RequestBody DishType dishType) {
        try {
            DishType dish = this.dishService.update(dishType);
            return ResponseEntity.ok().body(dish);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<HttpStatus>(e.getStatus());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(dishType);
        }
    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            this.dishService.delete(id);
            return ResponseEntity.ok().build();
        } catch (ResponseStatusException e) {
            return new ResponseEntity<HttpStatus>(e.getStatus());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(id);
        }
    }
}
