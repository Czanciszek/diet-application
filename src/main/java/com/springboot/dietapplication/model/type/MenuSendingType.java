package com.springboot.dietapplication.model.type;

import java.util.List;

public class MenuSendingType {

    private String id;

    private String startDate;

    private List<FoodType> foodTypes;

    private String patientId;

    private int weekCount;

    private float energyLimit;

    private float proteinsLimit;

    private float fatsLimit;

    private float carbohydratesLimit;

    public MenuSendingType() {

    }

    public MenuSendingType(
            String id,
            String startDate,
            List<FoodType> foodTypes,
            String patientId,
            int weekCount,
            float energyLimit,
            float proteinsLimit,
            float fatsLimit,
            float carbohydratesLimit) {

        this.id = id;
        this.startDate = startDate;
        this.foodTypes = foodTypes;
        this.patientId = patientId;
        this.weekCount = weekCount;
        this.energyLimit = energyLimit;
        this.proteinsLimit = proteinsLimit;
        this.fatsLimit = fatsLimit;
        this.carbohydratesLimit = carbohydratesLimit;
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

    public float getEnergyLimit() {
        return energyLimit;
    }

    public void setEnergyLimit(float energyLimit) {
        this.energyLimit = energyLimit;
    }

    public float getProteinsLimit() {
        return proteinsLimit;
    }

    public void setProteinsLimit(float proteinsLimit) {
        this.proteinsLimit = proteinsLimit;
    }

    public float getFatsLimit() {
        return fatsLimit;
    }

    public void setFatsLimit(float fatsLimit) {
        this.fatsLimit = fatsLimit;
    }

    public float getCarbohydratesLimit() {
        return carbohydratesLimit;
    }

    public void setCarbohydratesLimit(float carbohydratesLimit) {
        this.carbohydratesLimit = carbohydratesLimit;
    }
}
