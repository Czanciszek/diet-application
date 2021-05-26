package com.springboot.dietapplication.model.mongo.dish;

import com.springboot.dietapplication.model.type.FoodType;
import com.springboot.dietapplication.model.type.ProductDishType;
import com.springboot.dietapplication.model.type.DishType;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Dishes")
public class MongoDish {

    private String id;
    private String name;
    private List<ProductDishType> products;
    private FoodType foodType;
    private float portions;
    private String recipe;

    public MongoDish() {

    }

    public MongoDish(DishType dishType) {
        this.id = dishType.getId();
        this.name = dishType.getName();
        this.products = dishType.getProducts();
        this.foodType = dishType.getFoodType();
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
