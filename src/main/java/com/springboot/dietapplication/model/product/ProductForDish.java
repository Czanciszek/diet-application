package com.springboot.dietapplication.model.product;

import com.springboot.dietapplication.model.base.DocRef;

public class ProductForDish {

    private DocRef<Product> product;
    private float amount;
    private AmountType amountType;

    public ProductForDish(DocRef<Product> product, float amount, AmountType amountType) {
        this.product = product;
        this.amount = amount;
        this.amountType = amountType;
    }

    public DocRef<Product> getProduct() {
        return product;
    }

    public void setProduct(DocRef<Product> product) {
        this.product = product;
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
