package com.springboot.dietapplication.model.type;

import com.springboot.dietapplication.model.psql.menu.PsqlMeal;

import java.util.List;

public class MealType {

    private String id;

    private String name;

    private String dayMealId; // Reference to day

    private Long originMealId; // Reference to origin meal day

    private int isProduct; // true - for Product; false - for Dish

    private List<ProductDishType> productList; // Product list for dish type

    private float portions; // Portions of dish on meal

    private float grams; // Portions of dish on meal

    private float dishPortions; // Portions for full dish recipe (portions from DishType)

    private String recipe; // Recipe for dish meal

    private FoodType foodType; //Rodzaj posiłku

    public MealType() {

    }

    public MealType(MealType meal) {
        this.name = meal.name;
        this.dayMealId = meal.dayMealId;
        this.originMealId = meal.originMealId;
        this.isProduct = meal.isProduct;
        this.productList = meal.productList;
        this.portions = meal.portions;
        this.grams = meal.grams;
        this.dishPortions = meal.dishPortions;
        this.recipe = meal.recipe;
        this.foodType = meal.foodType;
    }

    public MealType(PsqlMeal meal) {
        if (meal.getId() > 0)
            this.id = String.valueOf(meal.getId());
        if (meal.getDayMealId() > 0)
            this.dayMealId = String.valueOf(meal.getDayMealId());
        this.originMealId = meal.getOriginMealId();
        this.name = meal.getName();
        this.isProduct = (meal.isProduct() ? 1 : 0);
        this.portions = meal.getPortions();
        this.grams = meal.getGrams();
        this.dishPortions = meal.getDishPortions();
        this.recipe = meal.getRecipe();
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

    public Long getOriginMealId() {
        return originMealId;
    }

    public void setOriginMealId(Long originMealId) {
        this.originMealId = originMealId;
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

    public FoodType getFoodType() {
        return foodType;
    }

    public void setFoodType(FoodType foodType) {
        this.foodType = foodType;
    }
}
