package com.springboot.dietapplication.model.type;

public class ProductAmountType {

    private AmountType amountType;

    private float grams;

    public ProductAmountType() {
    }

    public ProductAmountType(AmountType amountType, float grams) {
        this.amountType = amountType;
        this.grams = grams;
    }

    public AmountType getAmountType() {
        return amountType;
    }

    public void setAmountType(AmountType amountType) {
        this.amountType = amountType;
    }

    public float getGrams() {
        return grams;
    }

    public void setGrams(float grams) {
        this.grams = grams;
    }


}
