package com.springboot.dietapplication.controller.psql;

import com.springboot.dietapplication.model.type.MealType;
import com.springboot.dietapplication.service.psql.PsqlMealService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/psql/meals")
public class PsqlMealController {

    private final PsqlMealService mealService;

    public PsqlMealController(PsqlMealService mealService) {
        this.mealService = mealService;
    }

    @GetMapping
    public List<MealType> getAll() {
        return this.mealService.getAll();
    }

    @GetMapping(path = "/{dayMealId}")
    public MealType getMealById(@PathVariable("dayMealId") Long dayMealId) {
        return this.mealService.getMealById(dayMealId);
    }

    @GetMapping(path = "/list/{dayMealIdList}")
    public List<MealType> getMealsByDayMealList(@PathVariable("dayMealIdList") List<String> dayMealIdList) {
        return this.mealService.getMealsByDayMealList(dayMealIdList);
    }

    @PostMapping(produces = "application/json")
    ResponseEntity<MealType> insertMeal(@RequestBody MealType meal) {
        this.mealService.insert(meal);
        return ResponseEntity.ok().body(meal);
    }

    @PostMapping(path="/copy", produces = "application/json")
    ResponseEntity<MealType> copyMeal(@RequestBody MealType meal) {
        MealType newMeal = new MealType(meal);
        this.mealService.insert(newMeal);
        return ResponseEntity.ok().body(newMeal);
    }

    @PutMapping(path = "/{mealId}", produces = "application/json")
    ResponseEntity<MealType> update(@RequestBody MealType meal) {
        this.mealService.insert(meal);
        return ResponseEntity.ok().body(meal);
    }

    @DeleteMapping(path = "/{mealId}")
    ResponseEntity<MealType> deleteMeal(@PathVariable Long mealId) {
        this.mealService.delete(mealId);
        return ResponseEntity.ok().build();
    }
}
