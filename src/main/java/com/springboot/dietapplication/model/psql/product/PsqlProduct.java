package com.springboot.dietapplication.model.psql.product;

import com.springboot.dietapplication.model.excel.ProductExcel;
import com.springboot.dietapplication.model.type.ProductType;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "products")
public class PsqlProduct implements Serializable {

    private static final long serialVersionUID = 1019041668421449565L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "food_properties_id")
    private Long foodPropertiesId;

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
        this.id = productType.getId();
        this.name = productType.getName();
        this.lactose = productType.isLactose();
        this.starch = productType.isStarch();
        this.gluten = productType.isGluten();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getFoodPropertiesId() {
        return foodPropertiesId;
    }

    public void setFoodPropertiesId(Long foodPropertiesId) {
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