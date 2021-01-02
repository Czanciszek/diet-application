package com.springboot.dietapplication.model.product;

import com.springboot.dietapplication.model.base.BaseDoc;
import com.springboot.dietapplication.model.excel.ProductExcel;
import com.springboot.dietapplication.model.properties.FoodProperties;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Products")
public class Product extends BaseDoc {

    private String category;
    private String subcategory;
    private FoodProperties foodProperties;

    private boolean lactose;
    private boolean starch;
    private boolean gluten;

    protected Product() {

    }

    public Product(ProductExcel productExcel) {
        setName(productExcel.getName());
        this.category = productExcel.getCategory();
        this.subcategory = productExcel.getSubcategory();

        FoodProperties foodProperties = new FoodProperties();
        foodProperties.setEnergyValue(productExcel.getEnergyValue());
        foodProperties.setProteins(productExcel.getProteins());
        foodProperties.setFats(productExcel.getFats());
        foodProperties.setSaturatedFattyAcids(productExcel.getSaturatedFattyAcids());
        foodProperties.setMonoUnsaturatedFattyAcids(productExcel.getMonoUnsaturatedFattyAcids());
        foodProperties.setPolyUnsaturatedFattyAcids(productExcel.getPolyUnsaturatedFattyAcids());
        foodProperties.setCholesterol(productExcel.getCholesterol());
        foodProperties.setCarbohydrates(productExcel.getCarbohydrates());
        foodProperties.setSucrose(productExcel.getSucrose());
        foodProperties.setDietaryFibres(productExcel.getDietaryFibres());
        foodProperties.setSodium(productExcel.getSodium());
        foodProperties.setPotassium(productExcel.getPotassium());
        foodProperties.setCalcium(productExcel.getCalcium());
        foodProperties.setPhosphorus(productExcel.getPhosphorus());
        foodProperties.setMagnesium(productExcel.getMagnesium());
        foodProperties.setIron(productExcel.getIron());
        foodProperties.setSelenium(productExcel.getSelenium());
        foodProperties.setBetaCarotene(productExcel.getBetaCarotene());
        foodProperties.setVitaminD(productExcel.getVitaminD());
        foodProperties.setVitaminC(productExcel.getVitaminC());

        this.foodProperties = foodProperties;
        this.lactose = productExcel.getLactose() != null && !productExcel.getLactose().isEmpty();
        this.starch = productExcel.getStarch() != null && !productExcel.getStarch().isEmpty();
        this.gluten = productExcel.getGluten() != null && !productExcel.getGluten().isEmpty();
    }

    public String getCategory() {
        return category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public FoodProperties getFoodProperties() {
        return foodProperties;
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