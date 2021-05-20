package com.springboot.dietapplication.model.mongo.menu;

import com.springboot.dietapplication.model.type.FoodPropertiesType;
import com.springboot.dietapplication.model.type.MealType;
import com.springboot.dietapplication.model.type.MenuType;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Menus")
public class MongoMenu {

    private String id;

    private String patientId; // Ref - Dane pacjenta

    private String measurementId; // Ref - Dane pomiarowe

    private List<String> weekMealList; // Lista odnośników do tygodniowych jadłospisów

    private List<MealType> mealTypes; // Rodzaje posiłków do menu

    private String startDate; // Najwcześniejsza data z listy DayMeals

    private String endDate; // Najpóźniejsza data z listy DayMeals

    private FoodPropertiesType foodPropertiesType;

    private Float activityLevel;

    public MongoMenu() {

    }

    public MongoMenu(MenuType menuType) {
        this.startDate = menuType.getStartDate();
        this.mealTypes = menuType.getMealTypes();
        this.activityLevel = menuType.getActivityLevel();
        this.measurementId = menuType.getMeasurementId();
        this.patientId = menuType.getPatientId();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public FoodPropertiesType getFoodProperties() {
        return foodPropertiesType;
    }

    public void setFoodProperties(FoodPropertiesType foodPropertiesType) {
        this.foodPropertiesType = foodPropertiesType;
    }

    public Float getActivityLevel() {
        return activityLevel;
    }

    public void setActivityLevel(Float activityLevel) {
        this.activityLevel = activityLevel;
    }
}
