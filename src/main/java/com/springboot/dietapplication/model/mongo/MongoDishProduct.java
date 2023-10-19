package com.springboot.dietapplication.model.mongo;

import com.springboot.dietapplication.model.type.AmountType;
import com.springboot.dietapplication.model.type.FoodPropertiesType;
import com.springboot.dietapplication.model.type.ProductAmountType;
import com.springboot.dietapplication.model.type.ProductDishType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class MongoDishProduct {

    private String productId;
    private AmountType amountType;
    private Float grams;
    private Float amount;

    // Fields in dishes needed to be updated on every product update
    private String name;
    private FoodPropertiesType foodProperties;
    private List<ProductAmountType> availableAmountTypes;

    public MongoDishProduct() {}

    public MongoDishProduct(ProductDishType productDishType) {
        this.productId = String.valueOf(productDishType.getProductId());
        this.name = productDishType.getProductName();
        this.availableAmountTypes = productDishType.getAmountTypes();
        this.foodProperties = productDishType.getFoodPropertiesType();

        this.amountType = productDishType.getAmountType();
        this.grams = productDishType.getGrams();
        this.amount = productDishType.getAmount();
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public MongoDishProduct(ProductDishType productDishType, Optional<MongoProduct> mongoProduct) {
        if (mongoProduct.isPresent()) {
            this.name = mongoProduct.get().getName();
            this.foodProperties = mongoProduct.get().getFoodProperties();
            this.availableAmountTypes = mongoProduct.get().getAmountTypes();
        }

        this.productId = productDishType.getProductId();
        this.amountType = productDishType.getAmountType();
        this.grams = productDishType.getGrams();
        this.amount = productDishType.getAmount();
    }
}
