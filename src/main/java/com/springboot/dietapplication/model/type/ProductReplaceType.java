package com.springboot.dietapplication.model.type;

public class ProductReplaceType {

    private ReplacingProduct oldProduct;

    private ReplacingProduct newProduct;

    public ProductReplaceType() {
    }

    public ReplacingProduct getOldProduct() {
        return oldProduct;
    }

    public void setOldProduct(ReplacingProduct oldProduct) {
        this.oldProduct = oldProduct;
    }

    public ReplacingProduct getNewProduct() {
        return newProduct;
    }

    public void setNewProduct(ReplacingProduct newProduct) {
        this.newProduct = newProduct;
    }
}