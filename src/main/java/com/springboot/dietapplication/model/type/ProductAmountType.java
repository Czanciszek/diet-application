package com.springboot.dietapplication.model.type;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductAmountType {

    private AmountType amountType;

    private float grams;

    public ProductAmountType() {
    }

    public ProductAmountType(AmountType amountType, float grams) {
        this.amountType = amountType;
        this.grams = grams;
    }

}
