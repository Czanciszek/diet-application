package com.springboot.dietapplication.model.psql.menu;

import com.springboot.dietapplication.model.type.ProductDishType;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Deprecated(since = "0.1.0", forRemoval = true)
@Entity
@Table(name = "products_meals")
public class PsqlProductMeal implements Serializable {

    @Serial
    private static final long serialVersionUID = -1528940627693233878L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "meal_id")
    private Long mealId;

    @Column(name = "amount_type_id")
    private Long amountTypeId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "amount")
    private float amount;

    @Column(name = "grams")
    private float grams;

    public PsqlProductMeal() {
    }

    public PsqlProductMeal(PsqlProductMeal psqlProductMeal) {
        this.productId = psqlProductMeal.getProductId();
        this.amountTypeId = psqlProductMeal.getAmountTypeId();
        this.productName = psqlProductMeal.getProductName();
        this.amount = psqlProductMeal.getAmount();
        this.grams = psqlProductMeal.getGrams();
    }

    public PsqlProductMeal(ProductDishType productDish) {
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

    public Long getMealId() {
        return mealId;
    }

    public void setMealId(Long mealId) {
        this.mealId = mealId;
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
