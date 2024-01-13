package com.springboot.dietapplication.model.type;

import com.springboot.dietapplication.model.excel.ProductExcel;
import com.springboot.dietapplication.model.psql.product.PsqlProductFoodProperties;
import com.springboot.dietapplication.model.psql.properties.PsqlFoodProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Getter
@Setter
public class FoodPropertiesType implements Serializable {

    @Serial
    private static final long serialVersionUID = 8958334201429822540L;

    @Id
    private Long id;
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
        this.id = foodProperties.getId();
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

}
