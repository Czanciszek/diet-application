package com.springboot.dietapplication.model.psql.product;

import javax.persistence.*;

@Entity
public class PsqlShoppingProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "grams")
    private float grams;

    public PsqlShoppingProduct() {
    }

    public PsqlShoppingProduct(Long productId, String categoryName, String productName, float grams) {
        this.productId = productId;
        this.categoryName = categoryName;
        this.productName = productName;
        this.grams = grams;
    }

    public Long getProductId() {
        return productId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getProductName() {
        return productName;
    }

    public float getGrams() {
        return grams;
    }
}