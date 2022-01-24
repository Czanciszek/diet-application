package com.springboot.dietapplication.model.psql.menu;

import com.springboot.dietapplication.model.type.MealType;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "meals")
public class PsqlMeal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "day_meal_id")
    private long dayMealId;

    @Column(name = "origin_meal_id")
    private long originMealId;

    @Column(name = "food_type_id")
    private long foodTypeId;

    @Column(name = "name")
    private String name;

    @Column(name = "is_product")
    private boolean isProduct;

    @Column(name = "portions")
    private float portions;

    @Column(name = "grams")
    private float grams;

    @Column(name = "dish_portions")
    private float dishPortions;

    @Column(name = "recipe")
    private String recipe;


    public PsqlMeal() {

    }

    public PsqlMeal(MealType mealType) {
        if (mealType.getId() != null && !mealType.getId().isEmpty())
            this.id = Long.parseLong(mealType.getId());
        if (mealType.getDayMealId() != null && !mealType.getDayMealId().isEmpty())
            this.dayMealId = Long.parseLong(mealType.getDayMealId());
        this.originMealId = mealType.getOriginMealId();
        this.name = mealType.getName();
        this.isProduct = (mealType.getIsProduct() == 1);
        this.portions = mealType.getPortions();
        this.grams = mealType.getGrams();
        this.dishPortions = mealType.getDishPortions();
        this.recipe = mealType.getRecipe();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDayMealId() {
        return dayMealId;
    }

    public void setDayMealId(long dayMealId) {
        this.dayMealId = dayMealId;
    }

    public long getOriginMealId() {
        return originMealId;
    }

    public void setOriginMealId(long originMealId) {
        this.originMealId = originMealId;
    }

    public long getFoodTypeId() {
        return foodTypeId;
    }

    public void setFoodTypeId(long foodTypeId) {
        this.foodTypeId = foodTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isProduct() {
        return isProduct;
    }

    public void setProduct(boolean product) {
        isProduct = product;
    }

    public float getPortions() {
        return portions;
    }

    public void setPortions(float portions) {
        this.portions = portions;
    }

    public float getGrams() {
        return grams;
    }

    public void setGrams(float grams) {
        this.grams = grams;
    }

    public float getDishPortions() {
        return dishPortions;
    }

    public void setDishPortions(float dishPortions) {
        this.dishPortions = dishPortions;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }
}