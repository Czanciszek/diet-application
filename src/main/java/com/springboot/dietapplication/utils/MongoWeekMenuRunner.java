package com.springboot.dietapplication.utils;

import com.springboot.dietapplication.model.mongo.menu.MongoMeal;
import com.springboot.dietapplication.model.mongo.menu.MongoMenu;
import com.springboot.dietapplication.model.mongo.menu.MongoWeekMenu;
import com.springboot.dietapplication.model.psql.menu.PsqlDayMeal;
import com.springboot.dietapplication.model.psql.menu.PsqlWeekMeal;
import com.springboot.dietapplication.model.type.MealType;
import com.springboot.dietapplication.repository.DayMealRepository;
import com.springboot.dietapplication.repository.WeekMealRepository;
import com.springboot.dietapplication.repository.mongo.MongoMenuRepository;
import com.springboot.dietapplication.repository.mongo.MongoWeekMenuRepository;
import com.springboot.dietapplication.service.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MongoWeekMenuRunner {


    @Autowired
    MealService mealService;

    @Autowired
    DayMealRepository dayMealRepository;

    @Autowired
    WeekMealRepository weekMealRepository;

    @Autowired
    MongoMenuRepository mongoMenuRepository;

    @Autowired
    MongoWeekMenuRepository mongoWeekMenuRepository;

    public void reloadMenusPSQLtoMongo() {

        long start = System.currentTimeMillis();

        mongoWeekMenuRepository.deleteAll();
        long deleteAll = System.currentTimeMillis();

        List<MealType> mealTypes = mealService.getAll();
        List<PsqlDayMeal> psqlDayMeals = dayMealRepository.findAll();
        List<PsqlWeekMeal> psqlWeekMeals = weekMealRepository.findAll();
        long getPSQLData = System.currentTimeMillis();

        List<MongoMenu> mongoMenus = mongoMenuRepository.findAll();
        long getMongoData = System.currentTimeMillis();

        List<MongoWeekMenu> mongoWeekMenus = psqlWeekMeals.stream()
                .map(weekMeal -> {
                    List<PsqlDayMeal> dayMeals = psqlDayMeals.stream()
                            .filter(dayMeal -> dayMeal.getWeekMealId().equals(weekMeal.getId()))
                            .collect(Collectors.toList());
                    Optional<MongoMenu> mongoMenu = mongoMenus.stream()
                            .filter(mt -> mt.getId().equals(String.valueOf(weekMeal.getMenuId())))
                            .findFirst();

                    Map<String, List<MongoMeal>> mealDictionary = new HashMap<>();

                    String currentDate = DateFormatter.getInstance().getCurrentDate();

                    dayMeals.forEach(dm -> {
                        List<MealType> mealsInDay = mealTypes.stream()
                                .filter(m -> String.valueOf(dm.getId()).equals(m.getDayMealId()))
                                .collect(Collectors.toList());

                        Instant instantDate = Instant.parse(dm.getDate());
                        LocalDate localDate = Instant
                                .ofEpochMilli(instantDate.toEpochMilli())
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate();

                        List<MongoMeal> mongoMeals = mealsInDay.stream()
                                .map(MongoMeal::new)
                                .collect(Collectors.toList());

                        mongoMeals.forEach(meal -> {
                            meal.setCreationDate(currentDate);
                            meal.setUpdateDate(currentDate);
                        });

                        mealDictionary.put(localDate.toString(), mongoMeals);
                    });

                    MongoWeekMenu mongoWeekMenu = new MongoWeekMenu(weekMeal, mongoMenu);

                    mongoWeekMenu.setMeals(mealDictionary);
                    mongoWeekMenu.setCreationDate(currentDate);
                    mongoWeekMenu.setUpdateDate(currentDate);

                    return mongoWeekMenu;
                })
                .collect(Collectors.toList());
        long convertData = System.currentTimeMillis();

        mongoWeekMenuRepository.saveAll(mongoWeekMenus);
        long saveToRepo = System.currentTimeMillis();

        mongoWeekMenuRepository.findAll();
        long getMongoResult = System.currentTimeMillis();

        System.out.println("deleteAll: " + (deleteAll - start) + "ms");
        System.out.println("getPSQLData: " + (getPSQLData - deleteAll) + "ms");
        System.out.println("getMongoData: " + (getMongoData - getPSQLData) + "ms");
        System.out.println("convertData: " + (convertData - getMongoData) + "ms");
        System.out.println("saveToRepo: " + (saveToRepo - convertData) + "ms");
        System.out.println("getMongoResult: " + (getMongoResult - saveToRepo) + "ms");
    }
}
