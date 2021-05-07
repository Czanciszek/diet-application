package com.springboot.dietapplication.model.mongo.dish;

import com.springboot.dietapplication.model.type.ProductDishType;
import com.springboot.dietapplication.model.type.DishType;
import com.springboot.dietapplication.model.type.MealType;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Dishes")
public class MongoDish {

    private String id;
    private String name;
    private List<ProductDishType> products;
    private MealType mealType;
    private float portions;
    private String recipe;

    public MongoDish() {

    }

    public MongoDish(DishType dishType) {
        this.id = dishType.getId();
        this.name = dishType.getName();
        this.products = dishType.getProducts();
        this.mealType = dishType.getMealType();
        this.portions = dishType.getPortions();
        this.recipe = dishType.getRecipe();
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
