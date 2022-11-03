package com.springboot.dietapplication.model.type;

import com.springboot.dietapplication.model.psql.dish.PsqlProductDish;
import com.springboot.dietapplication.model.psql.menu.PsqlProductMeal;

import java.io.Serializable;
import java.util.List;

public class ProductDishType implements Serializable {

    private static final long serialVersionUID = -3141818667020448755L;

    private Long productId;
    private String productName;
    private float grams;
    private float amount;
    private AmountType amountType;
    private List<ProductAmountType> amountTypes;

    public ProductDishType() {

    }

    public ProductDishType(Long productId, String productName, float grams, float amount, AmountType amountType) {
        this.productId = productId;
        this.productName = productName;
        this.grams = grams;
        this.amount = amount;
        this.amountType = amountType;
    }

    public ProductDishType(PsqlProductDish psqlProductDish) {
        this.productId = psqlProductDish.getProductId();
        this.productName = psqlProductDish.getProductName();
        this.grams = psqlProductDish.getGrams();
        this.amount = psqlProductDish.getAmount();
    }

    public ProductDishType(PsqlProductMeal psqlProductMeal) {
        this.productId = psqlProductMeal.getProductId();
        this.productName = psqlProductMeal.getProductName();
        this.grams = psqlProductMeal.getGrams();
        this.amount = psqlProductMeal.getAmount();
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getGrams() {
        return grams;
    }

    public void setGrams(float grams) {
        this.grams = grams;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public AmountType getAmountType() {
        return amountType;
    }

    public void setAmountType(AmountType amountType) {
        this.amountType = amountType;
    }

    public List<ProductAmountType> getAmountTypes() {
        return amountTypes;
    }

    public void setAmountTypes(List<ProductAmountType> amountTypes) {
        this.amountTypes = amountTypes;
    }
}
