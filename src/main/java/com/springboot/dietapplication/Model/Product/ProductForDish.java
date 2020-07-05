package com.springboot.dietapplication.Model.Product;

import com.springboot.dietapplication.Model.Base.DocRef;
import com.springboot.dietapplication.Model.Product.Product;

public class ProductForDish {

    private DocRef<Product> product;
    private float amount;

    public ProductForDish(DocRef<Product> product, float amount) {
        this.product = product;
        this.amount = amount;
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
}
