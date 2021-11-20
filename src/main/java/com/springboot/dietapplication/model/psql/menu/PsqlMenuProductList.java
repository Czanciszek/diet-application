package com.springboot.dietapplication.model.psql.menu;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(PsqlMenuProductListKey.class)
public class PsqlMenuProductList implements Serializable {

    @Id
    @Column(name = "product_id")
    private long productId;

    @Id
    @Column(name = "meal_id")
    private long mealId;

    @Id
    @Column(name = "menu_id")
    private long menuId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "is_product")
    private boolean isProduct;

    @Column(name = "meal_name")
    private String mealName;

    @Column(name = "grams")
    private float grams;

    @Column(name = "amount")
    private float amount;

    @Column(name = "amount_type")
    private String amountType;

    @Column(name = "day_type")
    private String dayType;

    @Column(name = "date")
    private String date;

    public PsqlMenuProductList() {

    }

    public long getProductId() {
        return productId;
    }

    public long getMealId() {
        return mealId;
    }

    public long getMenuId() {
        return menuId;
    }

    public String getProductName() {
        return productName;
    }

    public boolean isProduct() {
        return isProduct;
    }

    public String getMealName() {
        return mealName;
    }

    public float getGrams() {
        return grams;
    }

    public float getAmount() {
        return amount;
    }

    public String getAmountType() {
        return amountType;
    }

    public String getDayType() {
        return dayType;
    }

    public String getDate() {
        return date;
    }
}

