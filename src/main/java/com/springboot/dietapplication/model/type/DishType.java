package com.springboot.dietapplication.model.type;

import com.springboot.dietapplication.model.psql.dish.PsqlDish;

import java.io.Serializable;
import java.util.List;

public class DishType implements Serializable {

    private static final long serialVersionUID = 7119598963939561319L;

    private Long id;
    private String name;
    private List<ProductDishType> products;
    private FoodType foodType;
    private float portions;
    private String recipe;

    public DishType() {

    }

    public DishType(PsqlDish dish) {
        this.id = dish.getId();
        this.name = dish.getName();
        this.portions = dish.getPortions();
        this.recipe = dish.getRecipe();
    }

    public DishType(DishType dish) {
        this.name = dish.getName();
        this.products = dish.getProducts();
        this.foodType = dish.getFoodType();
        this.portions = dish.getPortions();
        this.recipe = dish.getRecipe();
    }

    public DishType(List<ProductDishType> products) {
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public FoodType getFoodType() {
        return foodType;
    }

    public void setFoodType(FoodType foodType) {
        this.foodType = foodType;
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
