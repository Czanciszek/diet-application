package com.springboot.dietapplication.model.psql.product;

import com.springboot.dietapplication.model.excel.ProductExcel;
import com.springboot.dietapplication.model.type.ProductType;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "products")
public class PsqlProduct implements Serializable {

    @Serial
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

    public PsqlProduct() {
    }

    public PsqlProduct(ProductExcel productExcel) {
        this.name = productExcel.getName();
    }

    public PsqlProduct(ProductType productType) {
        this.id = Long.valueOf(productType.getId());
        this.name = productType.getName();
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

}