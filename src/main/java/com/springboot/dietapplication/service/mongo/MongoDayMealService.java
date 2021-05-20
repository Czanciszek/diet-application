package com.springboot.dietapplication.service.mongo;

import com.springboot.dietapplication.model.mongo.menu.MongoDayMeal;
import com.springboot.dietapplication.model.type.DayType;
import com.springboot.dietapplication.repository.mongo.MongoDayMealRepository;
import org.joda.time.DateTime;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MongoDayMealService {

    private final MongoDayMealRepository dayMealRepository;

    public MongoDayMealService(MongoDayMealRepository dayMealRepository) {
        this.dayMealRepository = dayMealRepository;
    }

    public List<MongoDayMeal> getAll() {
        return this.dayMealRepository.findAll();
    }

    public MongoDayMeal getDayMealById(String dayMealId) {
        Optional<MongoDayMeal> mongoDayMeal = this.dayMealRepository.findById(dayMealId);
        return mongoDayMeal.orElseGet(MongoDayMeal::new);
    }

    public List<MongoDayMeal> getDayMealByIdList(List<String> dayMealIdList) {
        Iterable<MongoDayMeal> mongoDayMeals = this.dayMealRepository.findAllById(dayMealIdList);
        return StreamSupport.stream(mongoDayMeals.spliterator(), false)
                .collect(Collectors.toList());
    }

    public List<String> generateDaysForWeek(DateTime date, String weekMealId) {
        List<String> dayMealList = new ArrayList<>();
        for (DayType dayType : DayType.values()) {
            MongoDayMeal dayMeal = new MongoDayMeal();
            dayMeal.setDayType(dayType);
            dayMeal.setDate(date.toString());
            dayMeal.setWeekMealId(weekMealId);
            insert(dayMeal);

            dayMealList.add(dayMeal.getId());
            date = date.plusDays(1);
        }

        return dayMealList;
    }

    public ResponseEntity<MongoDayMeal> insert(MongoDayMeal dayMeal) {
        dayMealRepository.save(dayMeal);
        return ResponseEntity.ok().body(dayMeal);
    }

    public ResponseEntity<MongoDayMeal> delete(String id) {
        dayMealRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
