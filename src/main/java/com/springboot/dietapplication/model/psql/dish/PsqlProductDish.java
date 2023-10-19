package com.springboot.dietapplication.model.psql.dish;

import com.springboot.dietapplication.model.type.ProductDishType;

import jakarta.persistence.*;

@Entity
@Table(name = "products_dishes")
public class PsqlProductDish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "dish_id")
    private Long dishId;

    @Column(name = "amount_type_id")
    private Long amountTypeId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "amount")
    private float amount;

    @Column(name = "grams")
    private float grams;

    public PsqlProductDish() {

    }

    public PsqlProductDish(ProductDishType productDish) {
        this.productId = Long.valueOf(productDish.getProductId());
        this.productName = productDish.getProductName();
        this.amount = productDish.getAmount();
        this.grams = productDish.getGrams();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getDishId() {
        return dishId;
    }

    public void setDishId(Long dishId) {
        this.dishId = dishId;
    }

    public Long getAmountTypeId() {
        return amountTypeId;
    }

    public void setAmountTypeId(Long amountTypeId) {
        this.amountTypeId = amountTypeId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float getGrams() {
        return grams;
    }

    public void setGrams(float grams) {
        this.grams = grams;
    }
}
