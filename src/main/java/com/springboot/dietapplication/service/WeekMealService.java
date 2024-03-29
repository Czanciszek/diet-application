package com.springboot.dietapplication.service;

import com.springboot.dietapplication.model.psql.menu.PsqlWeekMeal;
import com.springboot.dietapplication.model.type.WeekMealType;
import com.springboot.dietapplication.repository.WeekMealRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Deprecated(since = "0.1.0", forRemoval = true)
@Service
public class WeekMealService {

    @Autowired
    WeekMealRepository weekMealRepository;

    @Autowired
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

    public List<Long> getWeekMealIdList(Long menuId) {
        List<Long> weekMealIdList = new ArrayList<>();

        List<PsqlWeekMeal> weekMeals = this.weekMealRepository.getPsqlWeekMealsByMenuId(menuId);
        for (PsqlWeekMeal weekMeal : weekMeals) {
            weekMealIdList.add(weekMeal.getId());
        }

        return weekMealIdList;
    }

    public ResponseEntity<WeekMealType> insert(WeekMealType weekMeal) {
        PsqlWeekMeal psqlWeekMeal = new PsqlWeekMeal(weekMeal);
        weekMealRepository.save(psqlWeekMeal);
        weekMeal.setId(psqlWeekMeal.getId());
        return ResponseEntity.ok().body(weekMeal);
    }

    public void copy(long originMenuId, long newMenuId, float factor, String startDate) {
        List<PsqlWeekMeal> weekMeals = weekMealRepository.getPsqlWeekMealsByMenuId(originMenuId);
        DateTime dateTime = new DateTime(startDate);

        for (PsqlWeekMeal currentWeekMeal : weekMeals) {
            PsqlWeekMeal newWeekMeal = new PsqlWeekMeal();
            newWeekMeal.setMenuId(newMenuId);
            weekMealRepository.save(newWeekMeal);

            dayMealService.copyDayMeals(currentWeekMeal.getId(), newWeekMeal.getId(), factor, dateTime);
            dateTime = dateTime.plusDays(7);
        }
    }

    public void delete(Long id) {
        Optional<PsqlWeekMeal> weekMeal = weekMealRepository.findById(id);
        if (weekMeal.isEmpty()) return;

        // TODO: Get origin menu and check user authorization

        deleteWeekMeal(weekMeal.get());
    }

    public void deleteByMenuId(Long id) {
        List<PsqlWeekMeal> weekMeals = weekMealRepository.getPsqlWeekMealsByMenuId(id);
        for (PsqlWeekMeal weekMeal : weekMeals)
            deleteWeekMeal(weekMeal);
    }

    private void deleteWeekMeal(PsqlWeekMeal weekMeal) {
        dayMealService.deleteByWeekMealId(weekMeal.getId());
        weekMealRepository.deleteById(weekMeal.getId());
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

        List<Long> dayMealIdList = this.dayMealService.getDayMealIdList(weekMeal.getId());
        weekMealType.setDayMealList(dayMealIdList);

        return weekMealType;
    }

}
