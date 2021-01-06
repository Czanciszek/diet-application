package com.springboot.dietapplication.model.menu;

import com.springboot.dietapplication.model.base.BaseDoc;
import com.springboot.dietapplication.model.base.DocRef;
import com.springboot.dietapplication.model.product.AmountType;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Meals")
public class Meal extends BaseDoc {

    private DocRef<DayMeal> dayMealDocRef;

    private String foodId; //Id reference for product or dish

    private boolean isProduct; // 1 - for Product; 0 - for Dish

    private float portions; // Portions for dish meal

    private float amount; // Amount for product

    private AmountType amountType; // type of amount for product

    private MealType mealType; //Rodzaj posiłku

    public Meal() {

    }

    public Meal(DocRef<DayMeal> dayMealDocRef, String foodId, boolean isProduct, float portions, float amount, AmountType amountType, MealType mealType) {
        this.dayMealDocRef = dayMealDocRef;
        this.foodId = foodId;
        this.isProduct = isProduct;
        this.portions = portions;
        this.amount = amount;
        this.amountType = amountType;
        this.mealType = mealType;
    }

    public DocRef<DayMeal> getDayMealDocRef() {
        return dayMealDocRef;
    }

    public void setDayMealDocRef(DocRef<DayMeal> dayMealDocRef) {
        this.dayMealDocRef = dayMealDocRef;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public boolean isProduct() {
        return isProduct;
    }

    public void setProduct(boolean product) {
        isProduct = product;
    }

    public float getPortions() {
        return portions;
    }

    public void setPortions(float portions) {
        this.portions = portions;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public AmountType getAmountType() {
        return amountType;
    }

    public void setAmountType(AmountType amountType) {
        this.amountType = amountType;
    }

    public MealType getMealType() {
        return mealType;
    }

    public void setMealType(MealType mealType) {
        this.mealType = mealType;
    }
}
