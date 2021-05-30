package com.springboot.dietapplication.service.mongo;

import com.springboot.dietapplication.model.mongo.menu.MongoMeal;
import com.springboot.dietapplication.model.type.MealType;
import com.springboot.dietapplication.model.type.WeekMealType;
import com.springboot.dietapplication.repository.mongo.MongoMealRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MongoMealService {

    private final MongoMealRepository mealRepository;

    private final MongoWeekMealService weekMealService;

    public MongoMealService(MongoMealRepository mealRepository, MongoWeekMealService weekMealService) {
        this.mealRepository = mealRepository;
        this.weekMealService = weekMealService;
    }

    public List<MealType> getAll() {
        List<MongoMeal> mealList = this.mealRepository.findAll();
        return convertLists(mealList);
    }

    public MealType getMealById(String mealId) {
        Optional<MongoMeal> meal = this.mealRepository.findById(mealId);
        return meal.map(this::convertMongoDayMealToDayMealType).orElseGet(MealType::new);
    }

    public List<MealType> getMealsByWeekMealId(String weekMealId) {
        List<MongoMeal> mealList = new ArrayList<>();

        WeekMealType weekMealType = this.weekMealService.getWeekMealById(weekMealId);
        for (String dayMealId: weekMealType.getDayMealList()) {
            mealList.addAll(mealRepository.findByDayMealId(dayMealId));
        }
        return convertLists(mealList);
    }

    public MealType insert(MealType meal) {
        MongoMeal mongoMeal = new MongoMeal(meal);
        this.mealRepository.save(mongoMeal);
        meal.setId(mongoMeal.getId());
        return meal;
    }

    public void delete(String mealId) {
        this.mealRepository.deleteById(mealId);
    }

    private List<MealType> convertLists(List<MongoMeal> mealList) {
        List<MealType> mealTypeList = new ArrayList<>();
        for (MongoMeal meal : mealList) {
            mealTypeList.add(convertMongoDayMealToDayMealType(meal));
        }
        return mealTypeList;
    }

    private MealType convertMongoDayMealToDayMealType(MongoMeal meal) {
        return new MealType(meal);
    }

}
