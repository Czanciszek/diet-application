package com.springboot.dietapplication.service.mongo;

import com.springboot.dietapplication.model.mongo.menu.MongoDayMeal;
import com.springboot.dietapplication.model.type.DayMealType;
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

    public List<DayMealType> getAll() {
        List<MongoDayMeal> dayMealTypeList = this.dayMealRepository.findAll();
        return convertLists(dayMealTypeList);
    }

    public DayMealType getDayMealById(String dayMealId) {
        Optional<MongoDayMeal> mongoDayMeal = this.dayMealRepository.findById(dayMealId);
        return mongoDayMeal.map(this::convertMongoDayMealToDayMealType).orElseGet(DayMealType::new);
    }

    public List<DayMealType> getDayMealByIdList(List<String> dayMealIdList) {
        Iterable<MongoDayMeal> mongoDayMeals = this.dayMealRepository.findAllById(dayMealIdList);
        List<MongoDayMeal> dayMealList = StreamSupport.stream(mongoDayMeals.spliterator(), false)
                .collect(Collectors.toList());
        return convertLists(dayMealList);
    }

    public List<String> generateDaysForWeek(DateTime date, String weekMealId) {
        List<String> dayMealList = new ArrayList<>();
        for (DayType dayType : DayType.values()) {
            DayMealType dayMeal = new DayMealType();
            dayMeal.setDayType(dayType);
            dayMeal.setDate(date.toString());
            dayMeal.setWeekMealId(weekMealId);
            insert(dayMeal);

            dayMealList.add(dayMeal.getId());
            date = date.plusDays(1);
        }

        return dayMealList;
    }

    public ResponseEntity<DayMealType> insert(DayMealType dayMeal) {
        MongoDayMeal mongoDayMeal = new MongoDayMeal(dayMeal);
        dayMealRepository.save(mongoDayMeal);
        dayMeal.setId(mongoDayMeal.getId());
        return ResponseEntity.ok().body(dayMeal);
    }

    public ResponseEntity<DayMealType> delete(String id) {
        dayMealRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private List<DayMealType> convertLists(List<MongoDayMeal> dayMealList) {
        List<DayMealType> dayMealTypeList = new ArrayList<>();
        for (MongoDayMeal dayMeal : dayMealList) {
            dayMealTypeList.add(convertMongoDayMealToDayMealType(dayMeal));
        }
        return dayMealTypeList;
    }

    private DayMealType convertMongoDayMealToDayMealType(MongoDayMeal dayMeal) {
        return new DayMealType(dayMeal);
    }
}
