package com.springboot.dietapplication.model.psql.dish;

import com.springboot.dietapplication.model.type.ProductDishType;

import javax.persistence.*;

@Entity
@Table(name = "products_dishes")
public class PsqlProductDish {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "dish_id")
    private Long dishId;

    @Column(name = "amount_type_id")
    private Long amountTypeId;

    @Column(name = "amount")
    private float amount;

    @Column(name = "grams")
    private float grams;

    public PsqlProductDish() {

    }

    public PsqlProductDish(ProductDishType productDish) {
        this.productId = Long.parseLong(productDish.getProductId());
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
