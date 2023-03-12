package com.springboot.dietapplication.model.type;

import com.springboot.dietapplication.model.psql.product.PsqlProduct;
import com.springboot.dietapplication.model.psql.product.PsqlProductFoodProperties;

import java.io.Serializable;
import java.util.List;

public class ProductType implements Serializable {

    private static final long serialVersionUID = -6504989715893867042L;

    private Long id;
    private String name;
    private String category;
    private String subcategory;
    private FoodPropertiesType foodProperties;
    private List<ProductAmountType> amountTypes;
    private List<AllergenType> allergenTypes;

    public ProductType() {
    }

    public ProductType(PsqlProduct psqlProduct) {
        this.id = psqlProduct.getId();
        this.name = psqlProduct.getName();
    }

    public ProductType(PsqlProductFoodProperties psqlProduct) {
        this.id = psqlProduct.getId();
        this.name = psqlProduct.getName();
        this.category = psqlProduct.getCategory();
        this.subcategory = psqlProduct.getSubcategory();
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

    public List<ProductAmountType> getAmountTypes() {
        return amountTypes;
    }

    public void setAmountTypes(List<ProductAmountType> amountTypes) {
        this.amountTypes = amountTypes;
    }

    public List<AllergenType> getAllergenTypes() {
        return allergenTypes;
    }

    public void setAllergenTypes(List<AllergenType> allergenTypes) {
        this.allergenTypes = allergenTypes;
    }
}
