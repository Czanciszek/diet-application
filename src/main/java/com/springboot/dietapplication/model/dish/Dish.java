package com.springboot.dietapplication.model.dish;

import com.springboot.dietapplication.model.base.BaseDoc;
import com.springboot.dietapplication.model.type.MealType;
import com.springboot.dietapplication.model.mongo.product.ProductForDish;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Dishes")
public class Dish extends BaseDoc {

    private List<ProductForDish> products;
    private MealType mealType;
    private float portions;
    private String recipe;

    protected Dish() {

    }

    public Dish(List<ProductForDish> products) {
        this.products = products;
    }

    public List<ProductForDish> getProducts() {
        return products;
    }

    public void setProducts(List<ProductForDish> products) {
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
