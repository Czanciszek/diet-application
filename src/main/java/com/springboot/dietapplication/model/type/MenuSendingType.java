package com.springboot.dietapplication.model.type;

import java.io.Serializable;
import java.util.List;

public class MenuSendingType implements Serializable {

    private static final long serialVersionUID = -5136000565494491767L;

    private Long id;

    private String startDate;

    private List<FoodType> foodTypes;

    private Long patientId;

    private String recommendations;

    private int weekCount;

    private float energyLimit;

    private float proteinsLimit;

    private float fatsLimit;

    private float carbohydratesLimit;

    public MenuSendingType() {

    }

    public Long getId() {
        return id;
    }

    public String getStartDate() {
        return startDate;
    }

    public List<FoodType> getFoodTypes() {
        return foodTypes;
    }

    public Long getPatientId() {
        return patientId;
    }

    public String getRecommendations() {
        return recommendations;
    }

    public int getWeekCount() {
        return weekCount;
    }

    public float getEnergyLimit() {
        return energyLimit;
    }

    public float getProteinsLimit() {
        return proteinsLimit;
    }

    public float getFatsLimit() {
        return fatsLimit;
    }

    public float getCarbohydratesLimit() {
        return carbohydratesLimit;
    }
}
