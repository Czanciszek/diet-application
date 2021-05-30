package com.springboot.dietapplication.controller.mongo;

import com.springboot.dietapplication.model.type.DayMealType;
import com.springboot.dietapplication.service.mongo.MongoDayMealService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/mongo/daymeals")
public class MongoDayMealController {

    private final MongoDayMealService dayMealService;

    public MongoDayMealController(MongoDayMealService dayMealService) {
        this.dayMealService = dayMealService;
    }

    @GetMapping
    public List<DayMealType> getAll() {
        return this.dayMealService.getAll();
    }

    @GetMapping(path = "/list/{weekMealId}")
    public List<DayMealType> getDayMealByWeekMealId(@PathVariable("weekMealId") String weekMealId) {
        return this.dayMealService.getDayMealByWeekMealId(weekMealId);
    }

    @PostMapping(produces = "application/json")
    ResponseEntity<DayMealType> insertDayMeal(@RequestBody DayMealType dayMeal) {
        return this.dayMealService.insert(dayMeal);
    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity<DayMealType> deleteDayMeal(@PathVariable String id) {
        return this.dayMealService.delete(id);
    }
}
