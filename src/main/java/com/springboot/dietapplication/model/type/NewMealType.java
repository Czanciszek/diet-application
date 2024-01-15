package com.springboot.dietapplication.model.type;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NewMealType {

    String weekMenuId;

    String date;

    MealType meal;

}
