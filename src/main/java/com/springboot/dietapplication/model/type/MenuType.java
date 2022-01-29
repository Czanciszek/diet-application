package com.springboot.dietapplication.model.type;

import com.springboot.dietapplication.model.psql.menu.PsqlMenu;

import java.util.List;

public class MenuType {

    private String id;

    private String patientId; // Ref - Dane pacjenta

    private List<String> weekMealList; // Lista odnośników do tygodniowych jadłospisów

    private List<DayMealType> dayMealTypeList; //Lista odnośników do dni w jadłospisie

    private List<FoodType> foodTypes; // Rodzaje posiłków do menu

    private String startDate; // Najwcześniejsza data z listy DayMeals

    private String endDate; // Najpóźniejsza data z listy DayMeals

    private float energyLimit;

    private float proteinsLimit;

    private float fatsLimit;

    private float carbohydratesLimit;

    public MenuType() {

    }

    public MenuType(PsqlMenu menu) {
        if (menu.getId() > 0)
            this.id = String.valueOf(menu.getId());
        if (menu.getPatientId() > 0)
            this.patientId = String.valueOf(menu.getPatientId());
        this.startDate = menu.getStartDate();
        this.endDate = menu.getEndDate();
        this.energyLimit = menu.getEnergyLimit();
        this.proteinsLimit = menu.getProteinsLimit();
        this.fatsLimit = menu.getFatsLimit();
        this.carbohydratesLimit = menu.getCarbohydratesLimit();
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

    public List<String> getWeekMealList() {
        return weekMealList;
    }

    public void setWeekMealList(List<String> weekMealList) {
        this.weekMealList = weekMealList;
    }

    public List<DayMealType> getDayMealTypeList() {
        return dayMealTypeList;
    }

    public void setDayMealTypeList(List<DayMealType> dayMealTypeList) {
        this.dayMealTypeList = dayMealTypeList;
    }

    public List<FoodType> getFoodTypes() {
        return foodTypes;
    }

    public void setFoodTypes(List<FoodType> foodTypes) {
        this.foodTypes = foodTypes;
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
