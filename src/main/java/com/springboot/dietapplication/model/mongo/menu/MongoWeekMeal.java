package com.springboot.dietapplication.model.mongo.menu;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "WeekMeals")
public class MongoWeekMeal {

    private String id;

    private String menuId;

    private List<String> dayMealList; // Lista odnośników do dziennych jadłospisów

    //private Map<String, Float> limits; // Zużyte kaloryczności

    public MongoWeekMeal() {

    }

    public MongoWeekMeal(String menuId, List<String> dayMealList) {
        this.menuId = menuId;
        this.dayMealList = dayMealList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
