package com.springboot.dietapplication.model.menu;

import com.springboot.dietapplication.model.base.BaseDoc;
import com.springboot.dietapplication.model.base.DocRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "WeekMeals")
public class WeekMeal extends BaseDoc {

    private DocRef<Menu> menuDocRef;

    private List<String> dayMealList; // Lista odnośników do dziennych jadłospisów

    //private Map<String, Float> limits; // Zużyte kaloryczności

    public WeekMeal() {

    }

    public WeekMeal(DocRef<Menu> menuDocRef, List<String> dayMealList) {
        this.menuDocRef = menuDocRef;
        this.dayMealList = dayMealList;
    }

    public DocRef<Menu> getMenuDocRef() {
        return menuDocRef;
    }

    public void setMenuDocRef(DocRef<Menu> menuDocRef) {
        this.menuDocRef = menuDocRef;
    }

    public List<String> getDayMealList() {
        return dayMealList;
    }

    public void setDayMealList(List<String> dayMealList) {
        this.dayMealList = dayMealList;
    }

}
