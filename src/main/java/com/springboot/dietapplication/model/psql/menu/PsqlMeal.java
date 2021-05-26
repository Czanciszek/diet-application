package com.springboot.dietapplication.model.psql.menu;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "meals")
public class PsqlMeal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "day_meal_id")
    private long dayMealId;

    @Column(name = "meal_type_id")
    private long mealTypeId;

    @Column(name = "name")
    private String name;

    @Column(name = "is_product")
    private boolean isProduct;

    @Column(name = "portions")
    private int portions;

    @Column(name = "recipe")
    private String recipe;


    public PsqlMeal() {

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

    public long getMealTypeId() {
        return mealTypeId;
    }

    public void setMealTypeId(long mealTypeId) {
        this.mealTypeId = mealTypeId;
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

    public int getPortions() {
        return portions;
    }

    public void setPortions(int portions) {
        this.portions = portions;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }
}