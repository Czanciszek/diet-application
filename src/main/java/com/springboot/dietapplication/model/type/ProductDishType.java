package com.springboot.dietapplication.model.type;

import com.springboot.dietapplication.model.psql.dish.PsqlProductDish;

public class ProductDishType {

    private String productId;
    private float grams;
    private float amount;
    private AmountType amountType;

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
