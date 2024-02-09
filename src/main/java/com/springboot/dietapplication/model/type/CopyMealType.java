package com.springboot.dietapplication.model.type;

import lombok.Getter;

@Getter
public class CopyMealType {

    String menuId;

    String newDate;

    String originMealId;

    Float factor;

    public CopyMealType() { }

}
