package com.springboot.dietapplication.model.psql.product;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Deprecated(since = "0.1.0", forRemoval = true)
@Entity
@Table(name = "products_amount_types")
public class PsqlProductsAmountTypes implements Serializable {

    @Serial
    private static final long serialVersionUID = 8678132171650962678L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "amount_type_id")
    private Long amountTypeId;

    @Column(name = "grams")
    private float grams;

    public PsqlProductsAmountTypes() {

    }

    public PsqlProductsAmountTypes(Long productId, Long amountTypeId, float grams) {
        this.productId = productId;
        this.amountTypeId = amountTypeId;
        this.grams = grams;
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

    public Long getAmountTypeId() {
        return amountTypeId;
    }

    public void setAmountTypeId(Long amountTypeId) {
        this.amountTypeId = amountTypeId;
    }

    public float getGrams() {
        return grams;
    }

    public void setGrams(float grams) {
        this.grams = grams;
    }
}