package com.springboot.dietapplication.controller;

import com.springboot.dietapplication.model.type.DayMealType;
import com.springboot.dietapplication.service.DayMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/daymeals")
public class DayMealController {

    @Autowired
    DayMealService dayMealService;

    @GetMapping(path = "/list/{weekMealId}")
    public List<DayMealType> getDayMealByWeekMealId(@PathVariable("weekMealId") Long weekMealId) {
        return this.dayMealService.getDayMealByWeekMealId(weekMealId);
    }

    @PostMapping(produces = "application/json")
    ResponseEntity<DayMealType> insert(@RequestBody DayMealType dayMeal) {
        return this.dayMealService.insert(dayMeal);
    }

    @PostMapping(path="/copy/{originDayMealId}", produces = "application/json")
    ResponseEntity<DayMealType> copy(@RequestBody DayMealType dayMeal, @PathVariable Long originDayMealId) {
        this.dayMealService.copy(originDayMealId, dayMeal.getId());
        return ResponseEntity.ok().body(dayMeal);
    }

    @PostMapping(path="/clear", produces = "application/json")
    ResponseEntity<Long> clear(@RequestBody Long dayMealId) {
        this.dayMealService.clearDayMeal(dayMealId);
        return ResponseEntity.ok().body(dayMealId);
    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity<DayMealType> delete(@PathVariable Long id) {
        return this.dayMealService.delete(id);
    }

}
