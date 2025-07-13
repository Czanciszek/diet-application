package com.springboot.dietapplication.service;

import com.springboot.dietapplication.model.mongo.menu.MongoMeal;
import com.springboot.dietapplication.model.mongo.menu.MongoMenu;
import com.springboot.dietapplication.model.mongo.menu.MongoWeekMenu;
import com.springboot.dietapplication.model.type.*;
import com.springboot.dietapplication.repository.MongoMenuRepository;
import com.springboot.dietapplication.repository.MongoWeekMenuRepository;
import com.springboot.dietapplication.utils.DateFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MealServiceV2 {

    @Autowired
    MongoMenuRepository mongoMenuRepository;
    @Autowired
    MongoWeekMenuRepository mongoWeekMenuRepository;

    public List<MongoMeal> findMealsInMenu(String menuId) {

        List<MongoWeekMenu> weekMenus = mongoWeekMenuRepository.findByMenuId(menuId);

        return weekMenus.stream()
                .flatMap(weekMenu -> weekMenu.getMeals().entrySet().stream()
                        .flatMap(m -> m.getValue().stream()))
                .collect(Collectors.toList());
    }

    public List<MongoWeekMenu> weekMealsInMenu(String menuId) {
        return mongoWeekMenuRepository.findByMenuId(menuId);
    }

    public MealType insert(NewMealType newMealType) {
        MongoWeekMenu weekMenu = findWeekMenu(newMealType.getWeekMenuId());

        Map.Entry<String, List<MongoMeal>> day = weekMenu.getMeals()
                .entrySet()
                .stream()
                .filter(dayMeal -> dayMeal.getKey().equals(newMealType.getDate()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Day meal does not exist"));

        String currentDate = DateFormatter.getInstance().getCurrentDate();

        MongoMeal newMeal = new MongoMeal(newMealType.getMeal());
        newMeal.setId(UUID.randomUUID().toString());
        newMeal.setCreationDate(currentDate);
        newMeal.setUpdateDate(currentDate);
        newMeal.setAttachToRecipes(true);

        day.getValue().add(newMeal);
        weekMenu.setUpdateDate(currentDate);

        this.mongoWeekMenuRepository.save(weekMenu);

        return new MealType(newMeal);
    }

    public MealType update(NewMealType newMealType) {
        MongoWeekMenu weekMenu = findWeekMenu(newMealType.getWeekMenuId());

        MongoMeal originMeal = weekMenu.getMeals().values().stream()
                .flatMap(Collection::stream)
                .filter(meal -> meal.getId().equals(newMealType.getMeal().getId()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Meal does not exist"));

        originMeal.updateFrom(newMealType.getMeal());

        String currentDate = DateFormatter.getInstance().getCurrentDate();
        originMeal.setUpdateDate(currentDate);
        weekMenu.setUpdateDate(currentDate);

        this.mongoWeekMenuRepository.save(weekMenu);

        return new MealType(originMeal);
    }

    public MealType copyMeal(CopyMealType copyMealType) {
        MongoMenu mongoMenu = findMenu(copyMealType.getMenuId());

        List<MongoWeekMenu> weekMenus = this.mongoWeekMenuRepository.findAllById(mongoMenu.getWeekMenus());

        MongoWeekMenu updatedWeekMenu = weekMenus
                .stream()
                .filter(weekMenu -> weekMenu.getMeals().containsKey(copyMealType.getNewDate()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Target Day Meal does not exist"));

        MongoMeal originMeal = weekMenus
                .stream()
                .flatMap(w -> w.getMeals().entrySet().stream())
                .flatMap(d -> d.getValue().stream())
                .filter(meal -> meal.getId().equals(copyMealType.getOriginMealId()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Meal does not exist"));

        String currentDate = DateFormatter.getInstance().getCurrentDate();

        MongoMeal copyMeal = makeMealCopy(originMeal, currentDate, copyMealType.getFactor());

        updatedWeekMenu.getMeals().get(copyMealType.getNewDate()).add(copyMeal);
        updatedWeekMenu.setUpdateDate(currentDate);

        this.mongoWeekMenuRepository.saveAll(weekMenus);

        return new MealType(copyMeal);
    }

    public void copyDay(CopyDayMealsType copyDayMealsType) {
        MongoMenu mongoMenu = findMenu(copyDayMealsType.getMenuId());

        List<MongoWeekMenu> weekMenus = this.mongoWeekMenuRepository.findAllById(mongoMenu.getWeekMenus());

        Map.Entry<String, List<MongoMeal>> originDay = weekMenus
                .stream()
                .flatMap(w -> w.getMeals().entrySet().stream())
                .filter(dayMeal -> dayMeal.getKey().equals(copyDayMealsType.getOriginDate()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Origin Day meal does not exist"));

        Map.Entry<String, List<MongoMeal>> newDay = weekMenus
                .stream()
                .flatMap(w -> w.getMeals().entrySet().stream())
                .filter(dayMeal -> dayMeal.getKey().equals(copyDayMealsType.getNewDate()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Target day meal does not exist"));

        String currentDate = DateFormatter.getInstance().getCurrentDate();

        // Append all meals from origin day
        newDay.getValue().addAll(
                originDay
                        .getValue()
                        .stream()
                        .map(meals -> makeMealCopy(meals, currentDate, null))
                        .collect(Collectors.toList())
        );

        Optional<MongoWeekMenu> updatedWeekMenu = weekMenus
                .stream()
                .filter(weekMenu -> weekMenu.getMeals().containsKey(newDay.getKey()))
                .findFirst();
        updatedWeekMenu.ifPresent(mongoWeekMenu -> mongoWeekMenu.setUpdateDate(currentDate));

        this.mongoWeekMenuRepository.saveAll(weekMenus);
    }

    public void removeMeal(MealIdentifier mealIdentifier) {
        MongoWeekMenu weekMenu = findWeekMenu(mealIdentifier.getWeekMenuId());

        String currentDate = DateFormatter.getInstance().getCurrentDate();

        for (Map.Entry<String, List<MongoMeal>> dayMeal : weekMenu.getMeals().entrySet()) {

            // TODO: Consider setting deletionDate
            List<MongoMeal> meals = dayMeal.getValue()
                    .stream()
                    .filter(p -> !p.getId().equals(mealIdentifier.getMealId()))
                    .collect(Collectors.toList());

            if (dayMeal.getValue().size() != meals.size()) {
                dayMeal.setValue(meals);
                weekMenu.setUpdateDate(currentDate);
                this.mongoWeekMenuRepository.save(weekMenu);
                break;
            }
        }
    }

    public void clearDay(DayMealIdentifier dayMealIdentifier) {
        MongoWeekMenu weekMenu = findWeekMenu(dayMealIdentifier.getWeekMenuId());

        String currentDate = DateFormatter.getInstance().getCurrentDate();

        weekMenu.getMeals().entrySet().forEach(dayMeal -> {
            if (dayMeal.getKey().equals(dayMealIdentifier.getDate())) {
                dayMeal.setValue(new ArrayList<>());
                weekMenu.setUpdateDate(currentDate);
            }
        });

        this.mongoWeekMenuRepository.save(weekMenu);
    }

    private MongoWeekMenu findWeekMenu(String id) {
        return this.mongoWeekMenuRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Week does not exist"));
    }

    private MongoMenu findMenu(String menuId) {
        return this.mongoMenuRepository
                .findById(menuId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Menu does not exist"));
    }

    private MongoMeal makeMealCopy(MongoMeal originMeal, String currentDate, Float factor) {
        MongoMeal copyMeal = new MongoMeal(originMeal, currentDate);

        if (factor != null && !factor.equals(1.0f)) {
            copyMeal.setGrams(copyMeal.getGrams() * factor);
            copyMeal.getProducts().forEach(product -> product.setGrams(product.getGrams() * factor));
        }

        return copyMeal;
    }
}
