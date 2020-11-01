package com.springboot.dietapplication.model.menu;

import com.springboot.dietapplication.model.base.BaseDoc;
import com.springboot.dietapplication.model.base.DocRef;

import java.util.List;

public class DayMeal extends BaseDoc {

    DocRef<Menu> menuDocRef;

    List<Meal> mealList;

    public DayMeal(DocRef<Menu> menuDocRef, List<Meal> mealList) {
        this.menuDocRef = menuDocRef;
        this.mealList = mealList;
    }

    public DocRef<Menu> getMenuDocRef() {
        return menuDocRef;
    }

    public void setMenuDocRef(DocRef<Menu> menuDocRef) {
        this.menuDocRef = menuDocRef;
    }

    public List<Meal> getMealList() {
        return mealList;
    }

    public void setMealList(List<Meal> mealList) {
        this.mealList = mealList;
    }
}
