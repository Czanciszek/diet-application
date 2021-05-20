package com.springboot.dietapplication.service.mongo;

import com.springboot.dietapplication.model.mongo.menu.MongoDayMeal;
import com.springboot.dietapplication.model.mongo.menu.MongoMeal;
import com.springboot.dietapplication.repository.mongo.MongoDayMealRepository;
import com.springboot.dietapplication.repository.mongo.MongoMealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class MongoMealService {

    private final MongoMealRepository mealRepository;
    private final MongoDayMealRepository dayMealRepository;

    @Autowired
    public MongoMealService(MongoMealRepository mealRepository, MongoDayMealRepository dayMealRepository) {
        this.mealRepository = mealRepository;
        this.dayMealRepository = dayMealRepository;
    }

    public List<MongoMeal> getAllMeals() {
        return this.mealRepository.findAll();
    }

    public Optional<MongoMeal> getMealById(String mealId) {
        return this.mealRepository.findById(mealId);
    }

    public List<MongoMeal> getMealsByDayMealList(List<String> dayMealList) {
        List<MongoMeal> mealList = new ArrayList<>();
        for (String dayMealId: dayMealList) {
            mealList.addAll(mealRepository.findByDayMealIdLike(dayMealId));
        }
        return mealList;
    }

    public void createMeal(MongoMeal meal, boolean updateDayMeals) {
        mealRepository.save(meal);
        if (updateDayMeals)
            addNewMealToDayMeals(meal);
    }

    public void removeMeal(MongoMeal meal) {
        mealRepository.delete(meal);
        deleteMealFromDayMeals(meal);
    }

    private void addNewMealToDayMeals(MongoMeal meal) {
        Optional<MongoDayMeal> dayMeal = dayMealRepository.findById(meal.getDayMealId());
        if (dayMeal.isPresent()) {
            if (dayMeal.get().getMealList() != null)
                dayMeal.get().getMealList().add(meal.getId());
            else
                dayMeal.get().setMealList(Collections.singletonList(meal.getId()));
            dayMealRepository.save(dayMeal.get());
        }
    }

    private void deleteMealFromDayMeals(MongoMeal meal) {
        Optional<MongoDayMeal> dayMeal = dayMealRepository.findById(meal.getDayMealId());
        if (dayMeal.isPresent()) {
            if (dayMeal.get().getMealList() != null)
                dayMeal.get().getMealList().remove(meal.getId());
            dayMealRepository.save(dayMeal.get());
        }
    }
}
