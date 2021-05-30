package com.springboot.dietapplication.service.psql;

import com.springboot.dietapplication.model.psql.menu.PsqlDayMeal;
import com.springboot.dietapplication.model.type.DayMealType;
import com.springboot.dietapplication.model.type.DayType;
import com.springboot.dietapplication.model.type.WeekMealType;
import com.springboot.dietapplication.repository.psql.PsqlDayMealRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PsqlDayMealService {

    @Autowired
    PsqlDayMealRepository dayMealRepository;

    @Autowired
    PsqlWeekMealService weekMealService;

    public PsqlDayMealService(PsqlDayMealRepository dayMealRepository, @Lazy PsqlWeekMealService weekMealService) {
        this.dayMealRepository = dayMealRepository;
        this.weekMealService = weekMealService;
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

    public List<DayMealType> getDayMealByWeekMealId(String weekMealId) {
        List<DayMealType> dayMealTypeList = new ArrayList<>();

        WeekMealType weekMealType = this.weekMealService.getWeekMealById(Long.parseLong(weekMealId));
        for (String dayMealId : weekMealType.getDayMealList()) {
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

    private DayMealType convertPsqlDayMealToDayMealType(PsqlDayMeal dayMeal) {
        DayMealType dayMealType = new DayMealType(dayMeal);
        dayMealType.setDayType(DayType.valueOf(dayMeal.getDayType()));
        return dayMealType;
    }
}
