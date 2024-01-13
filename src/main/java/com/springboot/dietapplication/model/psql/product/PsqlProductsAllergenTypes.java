package com.springboot.dietapplication.model.psql.product;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Deprecated(since = "0.1.0", forRemoval = true)
@Entity
@Table(name = "products_allergens")
public class PsqlProductsAllergenTypes implements Serializable {

    @Serial
    private static final long serialVersionUID = 8470560288796186556L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "allergen_id")
    private Long allergenTypeId;

    public PsqlProductsAllergenTypes() {
    }

    public PsqlProductsAllergenTypes(Long productId, Long allergenTypeId) {
        this.productId = productId;
        this.allergenTypeId = allergenTypeId;
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

    public Long getAllergenTypeId() {
        return allergenTypeId;
    }

    public void setAllergenTypeId(Long allergenTypeId) {
        this.allergenTypeId = allergenTypeId;
    }
}
