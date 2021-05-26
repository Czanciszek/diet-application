package com.springboot.dietapplication.model.type;

import java.util.List;

public class MenuSendingType {

    private String id;

    private String startDate;

    private List<FoodType> foodTypes;

    private String measurementId;

    private String patientId;

    private int weekCount;

    private float activityLevel;

    public MenuSendingType() {

    }

    public MenuSendingType(String id,
                           String startDate,
                           List<FoodType> foodTypes,
                           String measurementId,
                           String patientId,
                           int weekCount,
                           float activityLevel) {
        this.id = id;
        this.startDate = startDate;
        this.foodTypes = foodTypes;
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

    public List<FoodType> getFoodTypes() {
        return foodTypes;
    }

    public void setFoodTypes(List<FoodType> foodTypes) {
        this.foodTypes = foodTypes;
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
