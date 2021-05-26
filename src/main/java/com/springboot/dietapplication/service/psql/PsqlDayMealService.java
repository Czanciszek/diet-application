package com.springboot.dietapplication.service.psql;

import com.springboot.dietapplication.model.psql.menu.PsqlDayMeal;
import com.springboot.dietapplication.model.type.DayMealType;
import com.springboot.dietapplication.model.type.DayType;
import com.springboot.dietapplication.repository.psql.PsqlDayMealRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PsqlDayMealService {

    @Autowired
    PsqlDayMealRepository dayMealRepository;

    public List<DayMealType> getAll() {
        List<PsqlDayMeal> dayMealTypeList = this.dayMealRepository.findAll();
        return convertLists(dayMealTypeList);
    }

    public DayMealType getDayMealById(Long dayMealId) {
        Optional<PsqlDayMeal> psqlDayMeal = this.dayMealRepository.findById(dayMealId);
        return psqlDayMeal.map(this::convertPsqlDayMealToDayMealType).orElseGet(DayMealType::new);
    }

    public List<String> getDayMealIdList(Long weekMealId) {
        List<String> dayMealIdList = new ArrayList<>();

        List<PsqlDayMeal> dayMeals = this.dayMealRepository.getPsqlDayMealsByWeekMealId(weekMealId);
        for (PsqlDayMeal dayMeal : dayMeals) {
            dayMealIdList.add(String.valueOf(dayMeal.getId()));
        }

        return dayMealIdList;
    }

    public List<DayMealType> getDayMealByIdList(List<String> dayMealIdList) {
        List<DayMealType> dayMealTypeList = new ArrayList<>();

        for (String dayMealId : dayMealIdList) {
            dayMealTypeList.add(this.getDayMealById(Long.parseLong(dayMealId)));
        }

        return dayMealTypeList;
    }

    public void generateDaysForWeek(DateTime date, Long weekMealId) {
        for (DayType dayType : DayType.values()) {
            DayMealType dayMeal = new DayMealType();
            dayMeal.setDayType(dayType);
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

    private List<DayMealType> convertLists(List<PsqlDayMeal> dayMealList) {
        List<DayMealType> dayMealTypeList = new ArrayList<>();
        for (PsqlDayMeal dayMeal : dayMealList) {
            dayMealTypeList.add(convertPsqlDayMealToDayMealType(dayMeal));
        }
        return dayMealTypeList;
    }

    private DayMealType convertPsqlDayMealToDayMealType(PsqlDayMeal dayMeal) {
        DayMealType dayMealType = new DayMealType(dayMeal);

        dayMealType.setDayType(DayType.valueOf(dayMeal.getDayType()));

        //List<String> dayMealIdList = this.mealService.getDayMealIdList(weekMeal.getId());
        //dayMealType.setDayMealList(dayMealIdList);
        dayMealType.setMealList(new ArrayList<>());

        return dayMealType;
    }
}
