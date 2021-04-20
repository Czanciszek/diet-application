package com.springboot.dietapplication.model.menu;

import com.springboot.dietapplication.model.base.BaseDoc;
import com.springboot.dietapplication.model.product.ProductForDish;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Meals")
public class Meal extends BaseDoc {

    private String dayMealId; // Reference to day

    private int isProduct; // true - for Product; false - for Dish

    private List<ProductForDish> productList; // Product list for dish type

    private float portions; // Portions for dish meal (For now should be always 1)

    private String recipe; // Recipe for dish meal

    private MealType mealType; //Rodzaj posiłku

    public Meal() {

    }

    public Meal(Meal meal) {
        this.setName(meal.getName());
        this.dayMealId = meal.dayMealId;
        this.isProduct = meal.isProduct;
        this.productList = meal.productList;
        this.portions = meal.portions;
        this.recipe = meal.recipe;
        this.mealType = meal.mealType;
    }

    public Meal(String dayMealId, String name, int isProduct,
                List<ProductForDish> productList, float portions,
                String recipe, MealType mealType) {
        this.setName(name);
        this.dayMealId = dayMealId;
        this.isProduct = isProduct;
        this.productList = productList;
        this.portions = portions;
        this.recipe = recipe;
        this.mealType = mealType;
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

    public List<ProductForDish> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductForDish> productList) {
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
