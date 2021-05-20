package com.springboot.dietapplication.controller.mongo;

import com.springboot.dietapplication.model.mongo.menu.MongoDayMeal;
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
    public List<MongoDayMeal> getAll() {
        return this.dayMealService.getAll();
    }

    @GetMapping(path = "/{dayMealId}")
    public MongoDayMeal getDayMealById(@PathVariable("dayMealId") String dayMealId) {
        return this.dayMealService.getDayMealById(dayMealId);
    }

    @GetMapping(path = "/list/{dayMealIdList}")
    public List<MongoDayMeal> getDayMealByIdList(@PathVariable("dayMealIdList") List<String> dayMealIdList) {
        return this.dayMealService.getDayMealByIdList(dayMealIdList);
    }

    @PostMapping(produces = "application/json")
    ResponseEntity<MongoDayMeal> insertDayMeal(@RequestBody MongoDayMeal dayMeal) {
        return this.dayMealService.insert(dayMeal);
    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity<MongoDayMeal> deleteDayMeal(@PathVariable String id) {
        return this.dayMealService.delete(id);
    }
}
