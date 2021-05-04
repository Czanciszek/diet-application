package com.springboot.dietapplication.model.type;

import com.springboot.dietapplication.model.mongo.product.MongoProduct;
import com.springboot.dietapplication.model.psql.product.PsqlProduct;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ProductType {

    @Id
    private String id;
    private String name;
    private String category;
    private String subcategory;
    @ManyToOne
    private FoodPropertiesType foodProperties;
    private boolean lactose;
    private boolean starch;
    private boolean gluten;

    public ProductType() {
    }

    public ProductType(MongoProduct mongoProduct) {
        this.id = mongoProduct.getId();
        this.name = mongoProduct.getName();
        this.lactose = mongoProduct.isLactose();
        this.starch = mongoProduct.isStarch();
        this.gluten = mongoProduct.isGluten();
    }

    public ProductType(PsqlProduct psqlProduct) {
        this.id = String.valueOf(psqlProduct.getId());
        this.name = psqlProduct.getName();
        this.lactose = psqlProduct.isLactose();
        this.starch = psqlProduct.isStarch();
        this.gluten = psqlProduct.isGluten();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
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

    public void setLactose(boolean lactose) {
        this.lactose = lactose;
    }

    public boolean isStarch() {
        return starch;
    }

    public void setStarch(boolean starch) {
        this.starch = starch;
    }

    public boolean isGluten() {
        return gluten;
    }

    public void setGluten(boolean gluten) {
        this.gluten = gluten;
    }
}
