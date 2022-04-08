package com.springboot.dietapplication.model.type;

import java.io.Serializable;
import java.util.List;

public class MenuSendingType implements Serializable {

    private static final long serialVersionUID = -5136000565494491767L;

    private Long id;

    private String startDate;

    private List<FoodType> foodTypes;

    private Long patientId;

    private int weekCount;

    private float energyLimit;

    private float proteinsLimit;

    private float fatsLimit;

    private float carbohydratesLimit;

    public MenuSendingType() {

    }

    public MenuSendingType(
            Long id,
            String startDate,
            List<FoodType> foodTypes,
            Long patientId,
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
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
