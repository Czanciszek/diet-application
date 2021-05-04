package com.springboot.dietapplication.model.psql.product;

import com.springboot.dietapplication.model.excel.ProductExcel;
import com.springboot.dietapplication.model.type.ProductType;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "products")
public class PsqlProduct implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "category_id")
    private long categoryId;

    @Column(name = "food_properties_id")
    private long foodPropertiesId;

    @Column(name = "lactose")
    private boolean lactose;

    @Column(name = "starch")
    private boolean starch;

    @Column(name = "gluten")
    private boolean gluten;

    public PsqlProduct() {
    }

    public PsqlProduct(ProductExcel productExcel) {
        this.name = productExcel.getName();
        this.lactose = productExcel.getLactose() != null && !productExcel.getLactose().isEmpty();
        this.starch = productExcel.getStarch() != null && !productExcel.getStarch().isEmpty();
        this.gluten = productExcel.getGluten() != null && !productExcel.getGluten().isEmpty();
    }

    public PsqlProduct(ProductType productType) {
        if (productType.getId() != null && !productType.getId().isEmpty())
            this.id = Long.parseLong(productType.getId());
        this.name = productType.getName();
        this.lactose = productType.isLactose();
        this.starch = productType.isStarch();
        this.gluten = productType.isGluten();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public long getFoodPropertiesId() {
        return foodPropertiesId;
    }

    public void setFoodPropertiesId(long foodPropertiesId) {
        this.foodPropertiesId = foodPropertiesId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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