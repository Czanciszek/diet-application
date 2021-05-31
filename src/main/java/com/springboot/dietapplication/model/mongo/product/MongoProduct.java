package com.springboot.dietapplication.model.mongo.product;

import com.springboot.dietapplication.model.excel.ProductExcel;
import com.springboot.dietapplication.model.type.ProductType;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Products")
public class MongoProduct {

    private String id;
    private String name;
    private String categoryId;
    private String foodPropertiesId;

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

    public String getFoodPropertiesId() {
        return foodPropertiesId;
    }

    public void setFoodPropertiesId(String foodPropertiesId) {
        this.foodPropertiesId = foodPropertiesId;
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