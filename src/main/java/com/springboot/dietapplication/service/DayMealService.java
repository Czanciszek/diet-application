package com.springboot.dietapplication.service;

import com.springboot.dietapplication.model.psql.menu.PsqlDayMeal;
import com.springboot.dietapplication.model.type.DayMealType;
import com.springboot.dietapplication.model.type.DayType;
import com.springboot.dietapplication.model.type.MealType;
import com.springboot.dietapplication.model.type.WeekMealType;
import com.springboot.dietapplication.repository.DayMealRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DayMealService {

    @Autowired DayMealRepository dayMealRepository;

    @Autowired @Lazy MealService mealService;
    @Autowired @Lazy WeekMealService weekMealService;

    public List<String> getDayMealIdList(Long weekMealId) {
        List<String> dayMealIdList = new ArrayList<>();

        List<PsqlDayMeal> dayMeals = this.dayMealRepository.getPsqlDayMealsByWeekMealId(weekMealId);
        for (PsqlDayMeal dayMeal : dayMeals) {
            dayMealIdList.add(String.valueOf(dayMeal.getId()));
        }

        return dayMealIdList;
    }

    public List<DayMealType> getDayMealByWeekMealId(String weekMealId) {
        List<DayMealType> dayMealTypeList = new ArrayList<>();

        WeekMealType weekMealType = this.weekMealService.getWeekMealById(Long.parseLong(weekMealId));
        for (String dayMealId : weekMealType.getDayMealList()) {
            dayMealTypeList.add(this.getDayMealById(Long.parseLong(dayMealId)));
        }

        return dayMealTypeList;
    }

    public void generateDaysForWeek(DateTime date, Long weekMealId) {
        for (int i = 0; i < 7; i++) {
            int dayTypeValue = date.getDayOfWeek()%7;
            Optional<DayType> dayType = DayType.valueOf(dayTypeValue);
            if (!dayType.isPresent()) continue;

            DayMealType dayMeal = new DayMealType();
            dayMeal.setDayType(dayType.get());
            dayMeal.setDate(date.toString());
            dayMeal.setWeekMealId(String.valueOf(weekMealId));
            insert(dayMeal);
            date = date.plusDays(1);
        }
    }

    public ResponseEntity<DayMealType> insert(DayMealType dayMeal) {
        PsqlDayMeal dayMealType = new PsqlDayMeal(dayMeal);
        dayMealRepository.save(dayMealType);
        dayMeal.setId(String.valueOf(dayMealType.getId()));
        return ResponseEntity.ok().body(dayMeal);
    }

    public ResponseEntity<DayMealType> delete(Long id) {
        dayMealRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private DayMealType getDayMealById(Long dayMealId) {
        Optional<PsqlDayMeal> psqlDayMeal = this.dayMealRepository.findById(dayMealId);
        return psqlDayMeal.map(this::convertPsqlDayMealToDayMealType).orElseGet(DayMealType::new);
    }

    private DayMealType convertPsqlDayMealToDayMealType(PsqlDayMeal dayMeal) {
        DayMealType dayMealType = new DayMealType(dayMeal);

        List<MealType> mealList =  this.mealService.getMealsByDayMealId(dayMeal.getId());
        List<Long> mealIdList = new ArrayList<>();
        for (MealType meal : mealList) {
            mealIdList.add(meal.getId());
        }
        dayMealType.setMealList(mealIdList);

        dayMealType.setDayType(DayType.valueOf(dayMeal.getDayType()));
        return dayMealType;
    }
}
