package com.springboot.dietapplication.model.type;

import com.springboot.dietapplication.model.excel.ProductExcel;
import com.springboot.dietapplication.model.psql.product.PsqlProductFoodProperties;
import com.springboot.dietapplication.model.psql.properties.PsqlFoodProperties;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class FoodPropertiesType {

    @Id
    private String id;
    private int energyValue;
    private float proteins;
    private float fats;
    private float saturatedFattyAcids;
    private float monoUnsaturatedFattyAcids;
    private float polyUnsaturatedFattyAcids;
    private float cholesterol;
    private float carbohydrates;
    private float sucrose;
    private float dietaryFibres;
    private float sodium;
    private float potassium;
    private float calcium;
    private float phosphorus;
    private float magnesium;
    private float iron;
    private float selenium;
    private float betaCarotene;
    private float vitaminD;
    private float vitaminC;

    public FoodPropertiesType() {

    }

    public FoodPropertiesType(PsqlFoodProperties foodProperties) {
        this.id = String.valueOf(foodProperties.getId());
        this.energyValue = foodProperties.getEnergyValue();
        this.proteins = foodProperties.getProteins();
        this.fats = foodProperties.getFats();
        this.saturatedFattyAcids = foodProperties.getSaturatedFattyAcids();
        this.monoUnsaturatedFattyAcids = foodProperties.getMonoUnsaturatedFattyAcids();
        this.polyUnsaturatedFattyAcids = foodProperties.getPolyUnsaturatedFattyAcids();
        this.cholesterol = foodProperties.getCholesterol();
        this.carbohydrates = foodProperties.getCarbohydrates();
        this.sucrose = foodProperties.getSucrose();
        this.dietaryFibres = foodProperties.getDietaryFibres();
        this.sodium = foodProperties.getSodium();
        this.potassium = foodProperties.getPotassium();
        this.calcium = foodProperties.getCalcium();
        this.phosphorus = foodProperties.getPhosphorus();
        this.magnesium = foodProperties.getMagnesium();
        this.iron = foodProperties.getIron();
        this.selenium = foodProperties.getSelenium();
        this.betaCarotene = foodProperties.getBetaCarotene();
        this.vitaminD = foodProperties.getVitaminD();
        this.vitaminC = foodProperties.getVitaminC();
    }

    public FoodPropertiesType(PsqlProductFoodProperties foodProperties) {
        this.energyValue = foodProperties.getEnergyValue();
        this.proteins = foodProperties.getProteins();
        this.fats = foodProperties.getFats();
        this.saturatedFattyAcids = foodProperties.getSaturatedFattyAcids();
        this.monoUnsaturatedFattyAcids = foodProperties.getMonoUnsaturatedFattyAcids();
        this.polyUnsaturatedFattyAcids = foodProperties.getPolyUnsaturatedFattyAcids();
        this.cholesterol = foodProperties.getCholesterol();
        this.carbohydrates = foodProperties.getCarbohydrates();
        this.sucrose = foodProperties.getSucrose();
        this.dietaryFibres = foodProperties.getDietaryFibres();
        this.sodium = foodProperties.getSodium();
        this.potassium = foodProperties.getPotassium();
        this.calcium = foodProperties.getCalcium();
        this.phosphorus = foodProperties.getPhosphorus();
        this.magnesium = foodProperties.getMagnesium();
        this.iron = foodProperties.getIron();
        this.selenium = foodProperties.getSelenium();
        this.betaCarotene = foodProperties.getBetaCarotene();
        this.vitaminD = foodProperties.getVitaminD();
        this.vitaminC = foodProperties.getVitaminC();
    }

    public FoodPropertiesType(ProductExcel productExcel) {
        this.energyValue = productExcel.getEnergyValue();
        this.proteins = productExcel.getProteins();
        this.fats = productExcel.getFats();
        this.saturatedFattyAcids = productExcel.getSaturatedFattyAcids();
        this.monoUnsaturatedFattyAcids = productExcel.getMonoUnsaturatedFattyAcids();
        this.polyUnsaturatedFattyAcids = productExcel.getPolyUnsaturatedFattyAcids();
        this.cholesterol = productExcel.getCholesterol();
        this.carbohydrates = productExcel.getCarbohydrates();
        this.sucrose = productExcel.getSucrose();
        this.dietaryFibres = productExcel.getDietaryFibres();
        this.sodium = productExcel.getSodium();
        this.potassium = productExcel.getPotassium();
        this.calcium = productExcel.getCalcium();
        this.phosphorus = productExcel.getPhosphorus();
        this.magnesium = productExcel.getMagnesium();
        this.iron = productExcel.getIron();
        this.selenium = productExcel.getSelenium();
        this.betaCarotene = productExcel.getBetaCarotene();
        this.vitaminD = productExcel.getVitaminD();
        this.vitaminC = productExcel.getVitaminC();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getEnergyValue() {
        return energyValue;
    }

    public void setEnergyValue(int energyValue) {
        this.energyValue = energyValue;
    }

    public float getProteins() {
        return proteins;
    }

    public void setProteins(float proteins) {
        this.proteins = proteins;
    }

    public float getFats() {
        return fats;
    }

    public void setFats(float fats) {
        this.fats = fats;
    }

    public float getSaturatedFattyAcids() {
        return saturatedFattyAcids;
    }

    public void setSaturatedFattyAcids(float saturatedFattyAcids) {
        this.saturatedFattyAcids = saturatedFattyAcids;
    }

    public float getMonoUnsaturatedFattyAcids() {
        return monoUnsaturatedFattyAcids;
    }

    public void setMonoUnsaturatedFattyAcids(float monoUnsaturatedFattyAcids) {
        this.monoUnsaturatedFattyAcids = monoUnsaturatedFattyAcids;
    }

    public float getPolyUnsaturatedFattyAcids() {
        return polyUnsaturatedFattyAcids;
    }

    public void setPolyUnsaturatedFattyAcids(float polyUnsaturatedFattyAcids) {
        this.polyUnsaturatedFattyAcids = polyUnsaturatedFattyAcids;
    }

    public float getCholesterol() {
        return cholesterol;
    }

    public void setCholesterol(float cholesterol) {
        this.cholesterol = cholesterol;
    }

    public float getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(float carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public float getSucrose() {
        return sucrose;
    }

    public void setSucrose(float sucrose) {
        this.sucrose = sucrose;
    }

    public float getDietaryFibres() {
        return dietaryFibres;
    }

    public void setDietaryFibres(float dietaryFibres) {
        this.dietaryFibres = dietaryFibres;
    }

    public float getSodium() {
        return sodium;
    }

    public void setSodium(float sodium) {
        this.sodium = sodium;
    }

    public float getPotassium() {
        return potassium;
    }

    public void setPotassium(float potassium) {
        this.potassium = potassium;
    }

    public float getCalcium() {
        return calcium;
    }

    public void setCalcium(float calcium) {
        this.calcium = calcium;
    }

    public float getPhosphorus() {
        return phosphorus;
    }

    public void setPhosphorus(float phosphorus) {
        this.phosphorus = phosphorus;
    }

    public float getMagnesium() {
        return magnesium;
    }

    public void setMagnesium(float magnesium) {
        this.magnesium = magnesium;
    }

    public float getIron() {
        return iron;
    }

    public void setIron(float iron) {
        this.iron = iron;
    }

    public float getSelenium() {
        return selenium;
    }

    public void setSelenium(float selenium) {
        this.selenium = selenium;
    }

    public float getBetaCarotene() {
        return betaCarotene;
    }

    public void setBetaCarotene(float betaCarotene) {
        this.betaCarotene = betaCarotene;
    }

    public float getVitaminD() {
        return vitaminD;
    }

    public void setVitaminD(float vitaminD) {
        this.vitaminD = vitaminD;
    }

    public float getVitaminC() {
        return vitaminC;
    }

    public void setVitaminC(float vitaminC) {
        this.vitaminC = vitaminC;
    }
}
