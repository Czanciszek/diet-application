package com.springboot.dietapplication.service;

import com.springboot.dietapplication.model.psql.menu.PsqlWeekMeal;
import com.springboot.dietapplication.model.type.WeekMealType;
import com.springboot.dietapplication.repository.WeekMealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WeekMealService {

    WeekMealRepository weekMealRepository;
    DayMealService dayMealService;

    public List<WeekMealType> getAll() {
        List<PsqlWeekMeal> weekMealList = this.weekMealRepository.findAll();
        return convertLists(weekMealList);
    }

    public WeekMealType getWeekMealById(Long weekMealId) {
        Optional<PsqlWeekMeal> psqlWeekMeal = this.weekMealRepository.findById(weekMealId);
        return psqlWeekMeal
                .map(this::convertPsqlWeekMealToWeekMealType)
                .orElseGet(WeekMealType::new);
    }

    public List<String> getWeekMealIdList(Long menuId) {
        List<String> weekMealIdList = new ArrayList<>();

        List<PsqlWeekMeal> weekMeals = this.weekMealRepository.getPsqlWeekMealsByMenuId(menuId);
        for (PsqlWeekMeal weekMeal : weekMeals) {
            weekMealIdList.add(String.valueOf(weekMeal.getId()));
        }

        return weekMealIdList;
    }

    public ResponseEntity<WeekMealType> insert(WeekMealType weekMeal) {
        PsqlWeekMeal psqlWeekMeal = new PsqlWeekMeal(weekMeal);
        weekMealRepository.save(psqlWeekMeal);
        weekMeal.setId(String.valueOf(psqlWeekMeal.getId()));
        return ResponseEntity.ok().body(weekMeal);
    }

    public ResponseEntity<WeekMealType> delete(Long id) {
        weekMealRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private List<WeekMealType> convertLists(List<PsqlWeekMeal> weekMealList) {
        List<WeekMealType> weekMealTypeList = new ArrayList<>();
        for (PsqlWeekMeal weekMeal : weekMealList) {
            weekMealTypeList.add(convertPsqlWeekMealToWeekMealType(weekMeal));
        }
        return weekMealTypeList;
    }

    private WeekMealType convertPsqlWeekMealToWeekMealType(PsqlWeekMeal weekMeal) {
        WeekMealType weekMealType = new WeekMealType(weekMeal);

        List<String> dayMealIdList = this.dayMealService.getDayMealIdList(weekMeal.getId());
        weekMealType.setDayMealList(dayMealIdList);

        return weekMealType;
    }

}
