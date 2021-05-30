package com.springboot.dietapplication.model.type;

import com.springboot.dietapplication.model.psql.dish.PsqlProductDish;
import com.springboot.dietapplication.model.psql.menu.PsqlProductMeal;

public class ProductDishType {

    private String productId;
    private float grams;
    private float amount;
    private AmountType amountType;

    public ProductDishType() {

    }

    public ProductDishType(String productId, float grams, float amount, AmountType amountType) {
        this.productId = productId;
        this.grams = grams;
        this.amount = amount;
        this.amountType = amountType;
    }

    public ProductDishType(PsqlProductDish psqlProductDish) {
        this.productId = String.valueOf(psqlProductDish.getProductId());
        this.grams = psqlProductDish.getGrams();
        this.amount = psqlProductDish.getAmount();
    }

    public ProductDishType(PsqlProductMeal psqlProductMeal) {
        this.productId = String.valueOf(psqlProductMeal.getProductId());
        this.grams = psqlProductMeal.getGrams();
        this.amount = psqlProductMeal.getAmount();
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

}
