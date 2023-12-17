package com.springboot.dietapplication.model.mongo.menu;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MongoDayMeals {

    private String date;
    private List<MongoMeal> meals;

    public MongoDayMeals() {}

    public MongoDayMeals(String date) {
        this.date = date;
        this.meals = new ArrayList<>();
    }

    public MongoDayMeals(String date, List<MongoMeal> meals) {
        this.date = date;
        this.meals = meals;
    }
}
