package com.springboot.dietapplication.model.product;

import com.springboot.dietapplication.model.base.DocRef;

public class ProductForDish {

    private DocRef<Product> product;
    private float grams;
    private float amount;
    private AmountType amountType;

    public ProductForDish(DocRef<Product> product, float grams, float amount, AmountType amountType) {
        this.product = product;
        this.grams = grams;
        this.amount = amount;
        this.amountType = amountType;
    }

    public DocRef<Product> getProduct() {
        return product;
    }

    public void setProduct(DocRef<Product> product) {
        this.product = product;
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
