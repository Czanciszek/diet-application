package com.springboot.dietapplication.model.dish;

import com.springboot.dietapplication.model.base.DocRef;

public class DishForMeal {

    private DocRef<Dish> dish;

    private float portions;

    public DishForMeal(DocRef<Dish> dish, float portions) {
        this.dish = dish;
        this.portions = portions;
    }

    public DocRef<Dish> getDish() {
        return dish;
    }

    public void setDish(DocRef<Dish> dish) {
        this.dish = dish;
    }

    public float getPortions() {
        return portions;
    }

    public void setPortions(float portions) {
        this.portions = portions;
    }
}
