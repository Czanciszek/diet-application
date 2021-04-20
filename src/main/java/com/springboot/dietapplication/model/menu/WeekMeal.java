package com.springboot.dietapplication.model.menu;

import com.springboot.dietapplication.model.base.BaseDoc;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "WeekMeals")
public class WeekMeal extends BaseDoc {

    private String menuId;

    private List<String> dayMealList; // Lista odnośników do dziennych jadłospisów

    //private Map<String, Float> limits; // Zużyte kaloryczności

    public WeekMeal() {

    }

    public WeekMeal(String menuId, List<String> dayMealList) {
        this.menuId = menuId;
        this.dayMealList = dayMealList;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public List<String> getDayMealList() {
        return dayMealList;
    }

    public void setDayMealList(List<String> dayMealList) {
        this.dayMealList = dayMealList;
    }

}
