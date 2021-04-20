package com.springboot.dietapplication.model.menu;

import com.springboot.dietapplication.model.base.BaseDoc;
import com.springboot.dietapplication.model.properties.FoodProperties;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Menus")
public class Menu extends BaseDoc {

    private String patientId; // Ref - Dane pacjenta

    private String measurementId; // Ref - Dane pomiarowe

    private List<String> weekMealList; // Lista odnośników do tygodniowych jadłospisów

    private List<MealType> mealTypes; // Rodzaje posiłków do menu

    private String startDate; // Najwcześniejsza data z listy DayMeals

    private String endDate; // Najpóźniejsza data z listy DayMeals

    private FoodProperties foodProperties;

    private Float activityLevel;

    public Menu() {

    }

    public Menu(Menu menu) {
        this.patientId = menu.patientId;
        this.measurementId = menu.measurementId;
        this.weekMealList = menu.weekMealList;
        this.mealTypes = menu.mealTypes;
        this.startDate = menu.startDate;
        this.endDate = menu.endDate;
        this.foodProperties = menu.foodProperties;
        this.activityLevel = menu.activityLevel;
    }

    public Menu(String patientId, String measurementId, List<String> weekMealList, List<MealType> mealTypes, String startDate, String endDate, FoodProperties foodProperties, Float activityLevel) {
        this.patientId = patientId;
        this.measurementId = measurementId;
        this.weekMealList = weekMealList;
        this.mealTypes = mealTypes;
        this.startDate = startDate;
        this.endDate = endDate;
        this.foodProperties = foodProperties;
        this.activityLevel = activityLevel;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getMeasurementId() {
        return measurementId;
    }

    public void setMeasurementId(String measurementId) {
        this.measurementId = measurementId;
    }

    public List<String> getWeekMealList() {
        return weekMealList;
    }

    public void setWeekMealList(List<String> weekMealList) {
        this.weekMealList = weekMealList;
    }

    public List<MealType> getMealTypes() {
        return mealTypes;
    }

    public void setMealTypes(List<MealType> mealTypes) {
        this.mealTypes = mealTypes;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public FoodProperties getFoodProperties() {
        return foodProperties;
    }

    public void setFoodProperties(FoodProperties foodProperties) {
        this.foodProperties = foodProperties;
    }

    public Float getActivityLevel() {
        return activityLevel;
    }

    public void setActivityLevel(Float activityLevel) {
        this.activityLevel = activityLevel;
    }
}
