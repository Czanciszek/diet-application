package com.springboot.dietapplication.model.type;

import com.springboot.dietapplication.model.psql.product.PsqlProductFoodProperties;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class CategoryType implements Serializable {

    @Serial
    private static final long serialVersionUID = 6325466471465045212L;

    private String id;
    private String category;
    private String subcategory;

    public CategoryType() {}

    public CategoryType(PsqlProductFoodProperties productFoodProperties) {
        this.id = String.valueOf(productFoodProperties.getCategoryId());
        this.category = productFoodProperties.getCategory();
        this.subcategory = productFoodProperties.getSubcategory();
    }
}
