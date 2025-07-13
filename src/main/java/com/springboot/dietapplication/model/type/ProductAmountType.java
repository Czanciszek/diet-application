package com.springboot.dietapplication.model.type;

import com.springboot.dietapplication.model.type.enums.AmountType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductAmountType {

    private AmountType amountType;

    private float grams;

    public ProductAmountType(AmountType amountType, float grams) {
        this.amountType = amountType;
        this.grams = grams;
    }

}
