package com.springboot.dietapplication.service;

import com.springboot.dietapplication.model.psql.menu.PsqlDayMeal;
import com.springboot.dietapplication.model.psql.menu.PsqlMenu;
import com.springboot.dietapplication.model.psql.menu.PsqlWeekMeal;
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

    @Autowired
    DayMealRepository dayMealRepository;

    @Autowired
    @Lazy
    MealService mealService;
    @Autowired
    @Lazy
    WeekMealService weekMealService;

    public List<Long> getDayMealIdList(Long weekMealId) {
        List<Long> dayMealIdList = new ArrayList<>();

        List<PsqlDayMeal> dayMeals = this.dayMealRepository.getPsqlDayMealsByWeekMealId(weekMealId);
        for (PsqlDayMeal dayMeal : dayMeals) {
            dayMealIdList.add(dayMeal.getId());
        }

        return dayMealIdList;
    }

    public List<DayMealType> getDayMealByWeekMealId(Long weekMealId) {
        List<DayMealType> dayMealTypeList = new ArrayList<>();

        WeekMealType weekMealType = this.weekMealService.getWeekMealById(weekMealId);
        for (Long dayMealId : weekMealType.getDayMealList()) {
            dayMealTypeList.add(this.getDayMealById(dayMealId));
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
            dayMeal.setWeekMealId(weekMealId);
            insert(dayMeal);
            date = date.plusDays(1);
        }
    }

    public ResponseEntity<DayMealType> insert(DayMealType dayMeal) {
        PsqlDayMeal dayMealType = new PsqlDayMeal(dayMeal);
        dayMealRepository.save(dayMealType);
        dayMeal.setId(dayMealType.getId());
        return ResponseEntity.ok().body(dayMeal);
    }

    public void copy(long originWeekMealId, long newWeekMealId, float factor) {
        List<PsqlDayMeal> dayMeals = dayMealRepository.getPsqlDayMealsByWeekMealId(originWeekMealId);
        for (PsqlDayMeal originDayMeal : dayMeals) {
            PsqlDayMeal newDayMeal = new PsqlDayMeal(originDayMeal);
            newDayMeal.setWeekMealId(newWeekMealId);
            dayMealRepository.save(newDayMeal);

            mealService.copy(originDayMeal.getId(), newDayMeal.getId(), factor);
        }
    }

    public ResponseEntity<DayMealType> delete(Long id) {
        dayMealRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    public void deleteByWeekMealId(Long id) {

        List<PsqlDayMeal> dayMeals = dayMealRepository.getPsqlDayMealsByWeekMealId(id);
        for (PsqlDayMeal dayMeal : dayMeals) {
            mealService.deleteByDayMealId(dayMeal.getId());
            dayMealRepository.deleteById(dayMeal.getId());
        }
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
