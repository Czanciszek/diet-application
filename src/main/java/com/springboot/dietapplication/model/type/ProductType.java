package com.springboot.dietapplication.model.type;

import com.springboot.dietapplication.model.psql.product.PsqlProduct;
import com.springboot.dietapplication.model.psql.product.PsqlProductFoodProperties;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
public class ProductType implements Serializable {

    private static final long serialVersionUID = -6504989715893867042L;

    @Id
    private Long id;
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

    public ProductType(PsqlProduct psqlProduct) {
        this.id = psqlProduct.getId();
        this.name = psqlProduct.getName();
        this.lactose = psqlProduct.isLactose();
        this.starch = psqlProduct.isStarch();
        this.gluten = psqlProduct.isGluten();
    }

    public ProductType(PsqlProductFoodProperties psqlProduct) {
        this.id = psqlProduct.getId();
        this.name = psqlProduct.getName();
        this.lactose = psqlProduct.isLactose();
        this.starch = psqlProduct.isStarch();
        this.gluten = psqlProduct.isGluten();
        this.category = psqlProduct.getCategory();
        this.subcategory = psqlProduct.getSubcategory();
        this.foodProperties = new FoodPropertiesType(psqlProduct);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
