package com.springboot.dietapplication.model.menu;

import com.springboot.dietapplication.model.type.MealType;

import java.util.List;

public class MenuType {

    private String id;

    private String startDate;

    private List<MealType> mealTypes;

    private String measurementId;

    private String patientId;

    private int weekCount;

    private float activityLevel;

    public MenuType() {

    }

    public MenuType(String id,
                    String startDate,
                    List<MealType> mealTypes,
                    String measurementId,
                    String patientId,
                    int weekCount,
                    float activityLevel) {
        this.id = id;
        this.startDate = startDate;
        this.mealTypes = mealTypes;
        this.measurementId = measurementId;
        this.patientId = patientId;
        this.weekCount = weekCount;
        this.activityLevel = activityLevel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public List<MealType> getMealTypes() {
        return mealTypes;
    }

    public void setMealTypes(List<MealType> mealTypes) {
        this.mealTypes = mealTypes;
    }

    public String getMeasurementId() {
        return measurementId;
    }

    public void setMeasurementId(String measurementId) {
        this.measurementId = measurementId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public int getWeekCount() {
        return weekCount;
    }

    public void setWeekCount(int weekCount) {
        this.weekCount = weekCount;
    }

    public float getActivityLevel() {
        return activityLevel;
    }

    public void setActivityLevel(float activityLevel) {
        this.activityLevel = activityLevel;
    }
}
