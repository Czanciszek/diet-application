package com.springboot.dietapplication.model.menu;

import com.springboot.dietapplication.model.base.BaseDoc;
import com.springboot.dietapplication.model.base.DocRef;
import com.springboot.dietapplication.model.dish.DishForMeal;
import com.springboot.dietapplication.model.product.ProductForDish;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Meals")
public class Meal extends BaseDoc {

    DocRef<DayMeal> dayMealDocRef;

    List<ProductForDish> productMealList;

    DishForMeal dishMeal;

    private MealType mealType; //Rodzaj posiłku

    public Meal() {

    }

    public Meal(DocRef<DayMeal> dayMealDocRef, List<ProductForDish> productMealList,
                DishForMeal dishMeal, MealType mealType) {
        this.dayMealDocRef = dayMealDocRef;
        this.productMealList = productMealList;
        this.dishMeal = dishMeal;
        this.mealType = mealType;
    }

    public DocRef<DayMeal> getDayMealDocRef() {
        return dayMealDocRef;
    }

    public void setDayMealDocRef(DocRef<DayMeal> dayMealDocRef) {
        this.dayMealDocRef = dayMealDocRef;
    }

    public List<ProductForDish> getProductMealList() {
        return productMealList;
    }

    public void setProductMealList(List<ProductForDish> productMealList) {
        this.productMealList = productMealList;
    }

    public DishForMeal getDishMeal() {
        return dishMeal;
    }

    public void setDishMeal(DishForMeal dishMeal) {
        this.dishMeal = dishMeal;
    }

    public MealType getMealType() {
        return mealType;
    }

    public void setMealType(MealType mealType) {
        this.mealType = mealType;
    }
}
