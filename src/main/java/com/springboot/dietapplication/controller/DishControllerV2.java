package com.springboot.dietapplication.controller;

import com.springboot.dietapplication.model.type.DishType;
import com.springboot.dietapplication.model.type.DishUsageType;
import com.springboot.dietapplication.service.DishServiceV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/v2/dishes")
public class DishControllerV2 {

    @Autowired
    DishServiceV2 dishService;

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
    ResponseEntity<?> dishUsage(@PathVariable String patientId) {
        try {
            List<DishUsageType> dishUsageTypes = this.dishService.getDishUsages(patientId);
            return ResponseEntity.ok(dishUsageTypes);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(produces = "application/json")
    ResponseEntity<?> insert(@RequestBody DishType dishType) {
        try {
            DishType dish = this.dishService.insert(dishType);
            return ResponseEntity.ok().body(dish);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<HttpStatus>(e.getStatusCode());
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
            return new ResponseEntity<HttpStatus>(e.getStatusCode());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(dishType);
        }
    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity<?> delete(@PathVariable String id) {
        try {
            this.dishService.delete(id);
            return ResponseEntity.ok().build();
        } catch (ResponseStatusException e) {
            return new ResponseEntity<HttpStatus>(e.getStatusCode());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(id);
        }
    }
}
