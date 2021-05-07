package com.springboot.dietapplication.model.type;

import com.springboot.dietapplication.model.mongo.dish.MongoDish;
import com.springboot.dietapplication.model.psql.dish.PsqlDish;

import java.util.List;

public class DishType {

    private String id;
    private String name;
    private List<ProductDishType> products;
    private MealType mealType;
    private float portions;
    private String recipe;

    public DishType() {

    }

    public DishType(MongoDish dish) {
        this.id = dish.getId();
        this.name = dish.getName();
        this.products = dish.getProducts();
        this.mealType = dish.getMealType();
        this.portions = dish.getPortions();
        this.recipe = dish.getRecipe();
    }

    public DishType(PsqlDish dish) {
        this.id = String.valueOf(dish.getId());
        this.name = dish.getName();
        this.portions = dish.getPortions();
        this.recipe = dish.getRecipe();
    }

    public DishType(List<ProductDishType> products) {
        this.products = products;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProductDishType> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDishType> products) {
        this.products = products;
    }

    public MealType getMealType() {
        return mealType;
    }

    public void setMealType(MealType mealType) {
        this.mealType = mealType;
    }

    public float getPortions() {
        return portions;
    }

    public void setPortions(float portions) {
        this.portions = portions;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }
}
