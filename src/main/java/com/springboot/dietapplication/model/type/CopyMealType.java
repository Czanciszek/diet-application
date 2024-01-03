package com.springboot.dietapplication.model.type;

import lombok.Getter;

@Getter
public class CopyMealType {

    String weekMenuId;

    String newDate;

    String originMealId;

    Float factor;

    public CopyMealType() { }

}
