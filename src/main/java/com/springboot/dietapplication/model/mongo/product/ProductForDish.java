package com.springboot.dietapplication.model.mongo.product;

import com.springboot.dietapplication.model.type.AmountType;

public class ProductForDish {

    private String productId;
    private float grams;
    private float amount;
    private AmountType amountType;

    public ProductForDish(String productId, float grams, float amount, AmountType amountType) {
        this.productId = productId;
        this.grams = grams;
        this.amount = amount;
        this.amountType = amountType;
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
