package com.springboot.dietapplication.controller;

import com.springboot.dietapplication.service.WeekMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Deprecated(since = "0.1.0", forRemoval = true)
@RestController
@RequestMapping("api/v1/weekmeals")
public class WeekMealController {

    @Autowired
    WeekMealService weekMealService;

    @DeleteMapping(path = "/{id}")
    ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            this.weekMealService.delete(id);
            return ResponseEntity.ok().build();
        } catch (ResponseStatusException e) {
            return new ResponseEntity<HttpStatus>(e.getStatusCode());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(id);
        }
    }

}
