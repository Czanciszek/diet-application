package com.springboot.dietapplication.model.mongo.product;

import com.springboot.dietapplication.model.excel.ProductExcel;
import com.springboot.dietapplication.model.type.FoodPropertiesType;
import com.springboot.dietapplication.model.type.ProductType;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Products")
public class MongoProduct {

    private String id;
    private String name;
    private String categoryId;
    private FoodPropertiesType foodProperties;

    private boolean lactose;
    private boolean starch;
    private boolean gluten;

    public MongoProduct() {

    }

    public MongoProduct(ProductExcel productExcel) {
        this.name = productExcel.getName();
        this.lactose = productExcel.getLactose() != null && !productExcel.getLactose().isEmpty();
        this.starch = productExcel.getStarch() != null && !productExcel.getStarch().isEmpty();
        this.gluten = productExcel.getGluten() != null && !productExcel.getGluten().isEmpty();
    }

    public MongoProduct(ProductType productType) {
        this.id = productType.getId();
        this.name = productType.getName();
        this.lactose = productType.isLactose();
        this.starch = productType.isStarch();
        this.gluten = productType.isGluten();
        this.foodProperties = productType.getFoodProperties();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public FoodPropertiesType getFoodProperties() {
        return foodProperties;
    }

    public void setFoodProperties(FoodPropertiesType foodProperties) {
        this.foodProperties = foodProperties;
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