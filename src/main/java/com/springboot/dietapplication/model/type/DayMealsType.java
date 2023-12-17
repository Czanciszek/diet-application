package com.springboot.dietapplication.model.type;

import com.springboot.dietapplication.model.mongo.menu.MongoDayMeals;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class DayMealsType implements Serializable {

    @Serial
    private static final long serialVersionUID = 7509851642318459313L;

    private String date;

    private List<MealType> meals;

    public DayMealsType() {}

    public DayMealsType(MongoDayMeals mongoDayMeals) {
        this.date = mongoDayMeals.getDate();
        this.meals = mongoDayMeals.getMeals().stream()
                .map(MealType::new)
                .collect(Collectors.toList());
    }
}
