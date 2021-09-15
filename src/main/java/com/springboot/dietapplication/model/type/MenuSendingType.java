package com.springboot.dietapplication.model.type;

import java.util.List;

public class MenuSendingType {

    private String id;

    private String startDate;

    private List<FoodType> foodTypes;

    private String measurementDate;

    private float patientWeight;

    private String patientId;

    private int weekCount;

    private float activityLevel;

    public MenuSendingType() {

    }

    public MenuSendingType(String id,
                           String startDate,
                           List<FoodType> foodTypes,
                           String measurementDate,
                           float patientWeight,
                           String patientId,
                           int weekCount,
                           float activityLevel) {
        this.id = id;
        this.startDate = startDate;
        this.foodTypes = foodTypes;
        this.measurementDate = measurementDate;
        this.patientWeight = patientWeight;
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

    public String getMeasurementDate() {
        return measurementDate;
    }

    public void setMeasurementDate(String measurementDate) {
        this.measurementDate = measurementDate;
    }

    public float getPatientWeight() {
        return patientWeight;
    }

    public void setPatientWeight(float patientWeight) {
        this.patientWeight = patientWeight;
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
