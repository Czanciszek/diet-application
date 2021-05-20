package com.springboot.dietapplication.model.mongo.menu;

import com.springboot.dietapplication.model.type.ProductDishType;
import com.springboot.dietapplication.model.type.MealType;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Meals")
public class MongoMeal {

    private String id;

    private String name;

    private String dayMealId; // Reference to day

    private int isProduct; // true - for Product; false - for Dish

    private List<ProductDishType> productList; // Product list for dish type

    private float portions; // Portions for dish meal (For now should be always 1)

    private String recipe; // Recipe for dish meal

    private MealType mealType; //Rodzaj posiłku

    public MongoMeal() {

    }

    public MongoMeal(MongoMeal meal) {
        this.name = meal.name;
        this.dayMealId = meal.dayMealId;
        this.isProduct = meal.isProduct;
        this.productList = meal.productList;
        this.portions = meal.portions;
        this.recipe = meal.recipe;
        this.mealType = meal.mealType;
    }

    public MongoMeal(String dayMealId, String name, int isProduct,
                     List<ProductDishType> productList, float portions,
                     String recipe, MealType mealType) {
        this.name = name;
        this.dayMealId = dayMealId;
        this.isProduct = isProduct;
        this.productList = productList;
        this.portions = portions;
        this.recipe = recipe;
        this.mealType = mealType;
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

    public String getDayMealId() {
        return dayMealId;
    }

    public void setDayMealId(String dayMealId) {
        this.dayMealId = dayMealId;
    }

    public int getIsProduct() {
        return isProduct;
    }

    public void setIsProduct(int isProduct) {
        this.isProduct = isProduct;
    }

    public List<ProductDishType> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductDishType> productList) {
        this.productList = productList;
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

    public MealType getMealType() {
        return mealType;
    }

    public void setMealType(MealType mealType) {
        this.mealType = mealType;
    }
}
