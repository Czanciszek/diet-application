package com.springboot.dietapplication.Model;

import com.springboot.dietapplication.Model.Excel.ProductExcel;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Products")
public class Product {
    @Id
    private String id;
    private String category;
    private String subcategory;
    private String name;
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
    private int sodium;
    private int potassium;
    private int calcium;
    private int phosphorus;
    private int magnesium;
    private float iron;
    private float selenium;
    private int betaCarotene;
    private int vitaminD;
    private int vitaminC;
    private boolean lactose;
    private boolean starch;
    private boolean gluten;

    protected Product() {

    }

    public Product(String category, String subcategory, String name,
                   int energyValue, float proteins, float fats,
                   float saturatedFattyAcids, float monoUnsaturatedFattyAcids, float polyUnsaturatedFattyAcids,
                   float cholesterol, float carbohydrates, float sucrose, float dietaryFibres,
                   int sodium, int potassium, int calcium, int phosphorus, int magnesium, float iron, float selenium,
                   int betaCarotene, int vitaminD, int vitaminC, boolean lactose, boolean starch, boolean gluten) {
        this.category = category;
        this.subcategory = subcategory;
        this.name = name;
        this.energyValue = energyValue;
        this.proteins = proteins;
        this.fats = fats;
        this.saturatedFattyAcids = saturatedFattyAcids;
        this.monoUnsaturatedFattyAcids = monoUnsaturatedFattyAcids;
        this.polyUnsaturatedFattyAcids = polyUnsaturatedFattyAcids;
        this.cholesterol = cholesterol;
        this.carbohydrates = carbohydrates;
        this.sucrose = sucrose;
        this.dietaryFibres = dietaryFibres;
        this.sodium = sodium;
        this.potassium = potassium;
        this.calcium = calcium;
        this.phosphorus = phosphorus;
        this.magnesium = magnesium;
        this.iron = iron;
        this.selenium = selenium;
        this.betaCarotene = betaCarotene;
        this.vitaminD = vitaminD;
        this.vitaminC = vitaminC;
        this.lactose = lactose;
        this.starch = starch;
        this.gluten = gluten;
    }

    public Product(ProductExcel productExcel) {
        this.category = productExcel.getCategory();
        this.subcategory = productExcel.getSubcategory();
        this.name = productExcel.getName();
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
        this.lactose = (productExcel.getLactose() != null) && !productExcel.getLactose().isEmpty();
        this.starch = (productExcel.getStarch() != null) && !productExcel.getStarch().isEmpty();
        this.gluten = (productExcel.getGluten() != null) && !productExcel.getGluten().isEmpty();
    }

    public String getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public String getName() {
        return name;
    }

    public int getEnergyValue() {
        return energyValue;
    }

    public float getProteins() {
        return proteins;
    }

    public float getFats() {
        return fats;
    }

    public float getSaturatedFattyAcids() {
        return saturatedFattyAcids;
    }

    public float getMonoUnsaturatedFattyAcids() {
        return monoUnsaturatedFattyAcids;
    }

    public float getPolyUnsaturatedFattyAcids() {
        return polyUnsaturatedFattyAcids;
    }

    public float getCholesterol() {
        return cholesterol;
    }

    public float getCarbohydrates() {
        return carbohydrates;
    }

    public float getSucrose() {
        return sucrose;
    }

    public float getDietaryFibres() {
        return dietaryFibres;
    }

    public int getSodium() {
        return sodium;
    }

    public int getPotassium() {
        return potassium;
    }

    public int getCalcium() {
        return calcium;
    }

    public int getPhosphorus() {
        return phosphorus;
    }

    public int getMagnesium() {
        return magnesium;
    }

    public float getIron() {
        return iron;
    }

    public float getSelenium() {
        return selenium;
    }

    public int getBetaCarotene() {
        return betaCarotene;
    }

    public int getVitaminD() {
        return vitaminD;
    }

    public int getVitaminC() {
        return vitaminC;
    }

    public boolean isLactose() {
        return lactose;
    }

    public boolean isStarch() {
        return starch;
    }

    public boolean isGluten() {
        return gluten;
    }
}