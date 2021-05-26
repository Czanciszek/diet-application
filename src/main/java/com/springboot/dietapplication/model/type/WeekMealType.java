package com.springboot.dietapplication.model.type;

import com.springboot.dietapplication.model.mongo.menu.MongoWeekMeal;
import com.springboot.dietapplication.model.psql.menu.PsqlWeekMeal;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "WeekMeals")
public class WeekMealType {

    private String id;

    private String menuId;

    private List<String> dayMealList; // Lista odnośników do dziennych jadłospisów

    //private Map<String, Float> limits; // Zużyte kaloryczności

    public WeekMealType() {

    }

    public WeekMealType(String menuId, List<String> dayMealList) {
        this.menuId = menuId;
        this.dayMealList = dayMealList;
    }

    public WeekMealType(MongoWeekMeal weekMeal) {
        this.id = weekMeal.getId();
        this.menuId = weekMeal.getMenuId();
        this.dayMealList = weekMeal.getDayMealList();
    }

    public WeekMealType(PsqlWeekMeal weekMeal) {
        if (weekMeal.getId() > 0)
            this.id = String.valueOf(weekMeal.getId());
        if (weekMeal.getMenuId() > 0)
            this.menuId = String.valueOf(weekMeal.getMenuId());
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
