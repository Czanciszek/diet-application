package com.springboot.dietapplication.service.mongo;

import com.springboot.dietapplication.model.mongo.menu.MongoWeekMeal;
import com.springboot.dietapplication.model.type.WeekMealType;
import com.springboot.dietapplication.repository.mongo.MongoWeekMealRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MongoWeekMealService {

    private final MongoWeekMealRepository weekMealRepository;

    public MongoWeekMealService(MongoWeekMealRepository weekMealRepository) {
        this.weekMealRepository = weekMealRepository;
    }

    public List<WeekMealType> getAll() {
        List<MongoWeekMeal> weekMealList = this.weekMealRepository.findAll();
        return convertMongoMenuToMenuType(weekMealList);
    }

    public WeekMealType getWeekMealById(String weekMealId) {
        Optional<MongoWeekMeal> mongoWeekMeal = this.weekMealRepository.findById(weekMealId);
        return mongoWeekMeal.map(WeekMealType::new).orElseGet(WeekMealType::new);
    }

    public ResponseEntity<WeekMealType> insert(WeekMealType weekMeal) {
        MongoWeekMeal mongoWeekMeal = new MongoWeekMeal(weekMeal);
        weekMealRepository.save(mongoWeekMeal);
        weekMeal.setId(mongoWeekMeal.getId());
        return ResponseEntity.ok().body(weekMeal);
    }

    public ResponseEntity<WeekMealType> delete(String id) {
        weekMealRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private List<WeekMealType> convertMongoMenuToMenuType(List<MongoWeekMeal> weekMealList) {
        List<WeekMealType> weekMealTypeList = new ArrayList<>();

        for (MongoWeekMeal weekMeal : weekMealList) {
            weekMealTypeList.add(new WeekMealType(weekMeal));
        }

        return weekMealTypeList;
    }
}
