package com.springboot.dietapplication.controller;

import com.springboot.dietapplication.model.menu.DayMeal;
import com.springboot.dietapplication.model.menu.Meal;
import com.springboot.dietapplication.repository.DayMealRepository;
import com.springboot.dietapplication.repository.MealRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/v1/meals")
public class MealController {

    private final MealRepository mealRepository;
    private final DayMealRepository dayMealRepository;

    public MealController(MealRepository mealRepository, DayMealRepository dayMealRepository) {
        this.mealRepository = mealRepository;
        this.dayMealRepository = dayMealRepository;
    }

    @GetMapping
    public List<Meal> getAll() {
        return this.mealRepository.findAll();
    }

    @GetMapping(path = "/list/{dayMealIdList}")
    public List<Meal> getMealsByDayMealList(@PathVariable("dayMealIdList") List<String> dayMealIdList) {
        List<Meal> mealList = new ArrayList<>();
        for (String dayMealId: dayMealIdList) {
            mealList.addAll(mealRepository.findByDayMealIdLike(dayMealId));
        }
        return mealList;
    }

    @PostMapping(produces = "application/json")
    ResponseEntity<Meal> insertMeal(@RequestBody Meal meal) {
        mealRepository.save(meal);

        Optional<DayMeal> dayMeal = dayMealRepository.findById(meal.getDayMealId());
        if (dayMeal.isPresent()) {
            if (dayMeal.get().getMealList() != null)
                dayMeal.get().getMealList().add(meal.getId());
            else
                dayMeal.get().setMealList(Collections.singletonList(meal.getId()));
            dayMealRepository.save(dayMeal.get());
        }

        return ResponseEntity.ok().body(meal);
    }

    @PostMapping(path="/copy", produces = "application/json")
    ResponseEntity<Meal> copyMeal(@RequestBody Meal meal) {
        Meal newMeal = new Meal(meal);
        mealRepository.save(newMeal);

        return ResponseEntity.ok().body(meal);
    }

    @PutMapping(path = "/{mealId}", produces = "application/json")
    ResponseEntity<Meal> updateMeal(@RequestBody Meal meal) {
        mealRepository.save(meal);
        return ResponseEntity.ok().body(meal);
    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity<Meal> deleteMeal(@PathVariable String id) {
        mealRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
