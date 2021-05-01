package com.springboot.dietapplication.model.psql.product;

import com.springboot.dietapplication.model.excel.ProductExcel;
import com.springboot.dietapplication.model.psql.properties.FoodProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "products")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "category_id")
    private long categoryId;

    private String category;

    @Column(name = "subcategory_id")
    private long subcategoryId;

    private String subcategory;

    @ManyToOne
    @JoinColumn(name = "food_properties_id")
    private FoodProperties foodProperties;

    @Column(name = "lactose")
    private boolean lactose;

    @Column(name = "starch")
    private boolean starch;

    @Column(name = "gluten")
    private boolean gluten;

    public Product() {
    }

    public Product(ProductExcel productExcel) {
        this.name = productExcel.getName();
        this.lactose = productExcel.getLactose() != null && !productExcel.getLactose().isEmpty();
        this.starch = productExcel.getStarch() != null && !productExcel.getStarch().isEmpty();
        this.gluten = productExcel.getGluten() != null && !productExcel.getGluten().isEmpty();
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(long subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public FoodProperties getFoodProperties() {
        return foodProperties;
    }

    public void setFoodProperties(FoodProperties foodProperties) {
        this.foodProperties = foodProperties;
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