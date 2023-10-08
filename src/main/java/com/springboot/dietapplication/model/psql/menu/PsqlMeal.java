package com.springboot.dietapplication.model.psql.menu;

import com.springboot.dietapplication.model.type.MealType;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "meals")
public class PsqlMeal implements Serializable {

    @Serial
    private static final long serialVersionUID = 7814192697591345299L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "day_meal_id")
    private Long dayMealId;

    @Column(name = "origin_meal_id")
    private Long originMealId;

    @Column(name = "base_dish_id")
    private Long baseDishId;

    @Column(name = "food_type_id")
    private Long foodTypeId;

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
        this.id = mealType.getId();
        this.dayMealId = mealType.getDayMealId();
        this.originMealId = mealType.getOriginMealId();
        this.baseDishId = mealType.getBaseDishId();
        this.name = mealType.getName();
        this.isProduct = (mealType.getIsProduct() == 1);
        this.portions = mealType.getPortions();
        this.grams = mealType.getGrams();
        this.dishPortions = mealType.getDishPortions();
        this.recipe = mealType.getRecipe();
    }

    public PsqlMeal(PsqlMeal psqlMeal) {
        this.name = psqlMeal.getName();
        this.foodTypeId = psqlMeal.getFoodTypeId();
        this.baseDishId = psqlMeal.getBaseDishId();
        this.isProduct = psqlMeal.isProduct();
        this.portions = psqlMeal.getPortions();
        this.dishPortions = psqlMeal.getDishPortions();
        this.recipe = psqlMeal.getRecipe();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getDayMealId() {
        return dayMealId;
    }

    public void setDayMealId(long dayMealId) {
        this.dayMealId = dayMealId;
    }

    public Long getOriginMealId() {
        return originMealId;
    }

    public void setOriginMealId(Long originMealId) {
        this.originMealId = originMealId;
    }

    public Long getBaseDishId() {
        return baseDishId;
    }

    public void setBaseDishId(Long baseDishId) {
        this.baseDishId = baseDishId;
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