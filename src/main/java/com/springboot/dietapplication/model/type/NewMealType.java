package com.springboot.dietapplication.model.type;

import lombok.Getter;

@Getter
public class NewMealType {

    String weekMenuId;

    String date;

    MealType meal;

    public NewMealType() {}

}
