package com.springboot.dietapplication.Model.Dish;

import com.springboot.dietapplication.Model.Base.BaseDoc;
import com.springboot.dietapplication.Model.Product.ProductForDish;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Dishes")
public class Dish extends BaseDoc {

    private List<ProductForDish> products;
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
