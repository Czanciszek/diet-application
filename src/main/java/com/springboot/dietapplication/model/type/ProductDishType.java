package com.springboot.dietapplication.model.type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springboot.dietapplication.model.mongo.dish.MongoDishProduct;
import com.springboot.dietapplication.model.type.enums.AmountType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductDishType implements Serializable {

    @Serial
    private static final long serialVersionUID = -3141818667020448755L;

    private String productId;
    private String productName;
    private float grams;
    private float amount;
    private AmountType amountType;
    private List<ProductAmountType> amountTypes;

    @JsonIgnore
    private FoodPropertiesType foodPropertiesType;

    public ProductDishType(MongoDishProduct mongoDishProduct) {
        this.productId = mongoDishProduct.getProductId();
        this.productName = mongoDishProduct.getName();
        this.grams = mongoDishProduct.getGrams();
        this.amount = mongoDishProduct.getAmount();
        this.amountType = mongoDishProduct.getAmountType();
        this.amountTypes = mongoDishProduct.getAvailableAmountTypes();
        this.foodPropertiesType = mongoDishProduct.getFoodProperties();
    }

}
