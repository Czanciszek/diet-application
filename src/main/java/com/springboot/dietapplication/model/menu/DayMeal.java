package com.springboot.dietapplication.model.menu;

import com.springboot.dietapplication.model.base.BaseDoc;
import com.springboot.dietapplication.model.base.DocRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "DayMeals")
public class DayMeal extends BaseDoc {

    private DocRef<Menu> menuDocRef;

    private DocRef<WeekMeal> weekMealDocRef;

    private List<String> mealList; // Lista odnośników do posiłków

    private DayType dayType; //Day name

    private String date;

    //private Map<String, Float> limits; // Limity kaloryczności

    public DayMeal() {

    }

    public DayMeal(DocRef<Menu> menuDocRef, DocRef<WeekMeal> weekMealDocRef, List<String> mealList, DayType dayType, String date) {
        this.menuDocRef = menuDocRef;
        this.weekMealDocRef = weekMealDocRef;
        this.mealList = mealList;
        this.dayType = dayType;
        this.date = date;
    }

    public DocRef<Menu> getMenuDocRef() {
        return menuDocRef;
    }

    public void setMenuDocRef(DocRef<Menu> menuDocRef) {
        this.menuDocRef = menuDocRef;
    }

    public DocRef<WeekMeal> getWeekMealDocRef() {
        return weekMealDocRef;
    }

    public void setWeekMealDocRef(DocRef<WeekMeal> weekMealDocRef) {
        this.weekMealDocRef = weekMealDocRef;
    }

    public List<String> getMealList() {
        return mealList;
    }

    public void setMealList(List<String> mealList) {
        this.mealList = mealList;
    }

    public DayType getDayType() {
        return dayType;
    }

    public void setDayType(DayType dayType) {
        this.dayType = dayType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
