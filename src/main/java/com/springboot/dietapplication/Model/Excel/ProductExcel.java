package com.springboot.dietapplication.Model.Excel;

import io.github.biezhi.excel.plus.annotation.ExcelColumn;

public class ProductExcel {

    @ExcelColumn(title = "category", index = 0)
    private String category;

    @ExcelColumn(title = "subcategory", index = 1)
    private String subcategory;

    @ExcelColumn(title = "name", index = 2)
    private String name;

    @ExcelColumn(title = "energyValue", index = 3)
    private int energyValue;

    @ExcelColumn(title = "proteins", index = 4)
    private float proteins;

    @ExcelColumn(title = "fats", index = 5)
    private float fats;

    @ExcelColumn(title = "saturatedFattyAcids", index = 6)
    private float saturatedFattyAcids;

    @ExcelColumn(title = "monoUnsaturatedFattyAcids", index = 7)
    private float monoUnsaturatedFattyAcids;

    @ExcelColumn(title = "polyUnsaturatedFattyAcids", index = 8)
    private float polyUnsaturatedFattyAcids;

    @ExcelColumn(title = "cholesterol", index = 9)
    private float cholesterol;

    @ExcelColumn(title = "carbohydrates", index = 10)
    private float carbohydrates;

    @ExcelColumn(title = "sucrose", index = 11)
    private float sucrose;

    @ExcelColumn(title = "dietaryFibres", index = 12)
    private float dietaryFibres;

    @ExcelColumn(title = "sodium", index = 13)
    private float sodium;

    @ExcelColumn(title = "potassium", index = 14)
    private float potassium;

    @ExcelColumn(title = "calcium", index = 15)
    private float calcium;

    @ExcelColumn(title = "phosphorus", index = 16)
    private float phosphorus;

    @ExcelColumn(title = "magnesium", index = 17)
    private float magnesium;

    @ExcelColumn(title = "iron", index = 18)
    private float iron;

    @ExcelColumn(title = "selenium", index = 19)
    private float selenium;

    @ExcelColumn(title = "betaCarotene", index = 20)
    private float betaCarotene;

    @ExcelColumn(title = "vitaminD", index = 21)
    private float vitaminD;

    @ExcelColumn(title = "vitaminC", index = 22)
    private float vitaminC;

    @ExcelColumn(title = "lactose", index = 23)
    private String lactose;

    @ExcelColumn(title = "starch", index = 24)
    private String starch;

    @ExcelColumn(title = "gluten", index = 25)
    private String gluten;

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

    public float getSodium() {
        return sodium;
    }

    public float getPotassium() {
        return potassium;
    }

    public float getCalcium() {
        return calcium;
    }

    public float getPhosphorus() {
        return phosphorus;
    }

    public float getMagnesium() {
        return magnesium;
    }

    public float getIron() {
        return iron;
    }

    public float getSelenium() {
        return selenium;
    }

    public float getBetaCarotene() {
        return betaCarotene;
    }

    public float getVitaminD() {
        return vitaminD;
    }

    public float getVitaminC() {
        return vitaminC;
    }

    public String getLactose() {
        return lactose;
    }

    public String getStarch() {
        return starch;
    }

    public String getGluten() {
        return gluten;
    }
}
