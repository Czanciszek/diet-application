package com.springboot.dietapplication.controller;

import com.springboot.dietapplication.model.type.DayMealType;
import com.springboot.dietapplication.service.DayMealService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/psql/daymeals")
public class DayMealController {

    private final DayMealService dayMealService;

    public DayMealController(DayMealService dayMealService) {
        this.dayMealService = dayMealService;
    }

    @GetMapping(path = "/list/{weekMealId}")
    public List<DayMealType> getDayMealByWeekMealId(@PathVariable("weekMealId") String weekMealId) {
        return this.dayMealService.getDayMealByWeekMealId(weekMealId);
    }

    @PostMapping(produces = "application/json")
    ResponseEntity<DayMealType> insert(@RequestBody DayMealType dayMeal) {
        return this.dayMealService.insert(dayMeal);
    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity<DayMealType> delete(@PathVariable Long id) {
        return this.dayMealService.delete(id);
    }

}
