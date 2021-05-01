package com.springboot.dietapplication.service;

import com.springboot.dietapplication.model.menu.DayMeal;
import com.springboot.dietapplication.model.menu.Meal;
import com.springboot.dietapplication.repository.mongo.DayMealRepository;
import com.springboot.dietapplication.repository.mongo.MealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class MealService {

    private final MealRepository mealRepository;
    private final DayMealRepository dayMealRepository;

    @Autowired
    public MealService(MealRepository mealRepository, DayMealRepository dayMealRepository) {
        this.mealRepository = mealRepository;
        this.dayMealRepository = dayMealRepository;
    }

    public List<Meal> getAllMeals() {
        return this.mealRepository.findAll();
    }

    public Optional<Meal> getMealById(String mealId) {
        return this.mealRepository.findById(mealId);
    }

    public List<Meal> getMealsByDayMealList(List<String> dayMealList) {
        List<Meal> mealList = new ArrayList<>();
        for (String dayMealId: dayMealList) {
            mealList.addAll(mealRepository.findByDayMealIdLike(dayMealId));
        }
        return mealList;
    }

    public void createMeal(Meal meal, boolean updateDayMeals) {
        mealRepository.save(meal);
        if (updateDayMeals)
            addNewMealToDayMeals(meal);
    }

    public void removeMeal(Meal meal) {
        mealRepository.delete(meal);
        deleteMealFromDayMeals(meal);
    }

    private void addNewMealToDayMeals(Meal meal) {
        Optional<DayMeal> dayMeal = dayMealRepository.findById(meal.getDayMealId());
        if (dayMeal.isPresent()) {
            if (dayMeal.get().getMealList() != null)
                dayMeal.get().getMealList().add(meal.getId());
            else
                dayMeal.get().setMealList(Collections.singletonList(meal.getId()));
            dayMealRepository.save(dayMeal.get());
        }
    }

    private void deleteMealFromDayMeals(Meal meal) {
        Optional<DayMeal> dayMeal = dayMealRepository.findById(meal.getDayMealId());
        if (dayMeal.isPresent()) {
            if (dayMeal.get().getMealList() != null)
                dayMeal.get().getMealList().remove(meal.getId());
            dayMealRepository.save(dayMeal.get());
        }
    }
}
