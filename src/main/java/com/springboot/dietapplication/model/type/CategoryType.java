package com.springboot.dietapplication.model.type;

import com.springboot.dietapplication.model.psql.product.PsqlProductFoodProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class CategoryType implements Serializable, Comparable<CategoryType> {

    @Serial
    private static final long serialVersionUID = 6325466471465045212L;

    private String id;
    private String category;
    private String subcategory;

    public CategoryType(PsqlProductFoodProperties productFoodProperties) {
        this.id = String.valueOf(productFoodProperties.getCategoryId());
        this.category = productFoodProperties.getCategory();
        this.subcategory = productFoodProperties.getSubcategory();
    }

    @Override
    public boolean equals(Object categoryType) {
        if (this == categoryType) {
            return true;
        }
        if (categoryType == null || getClass() != categoryType.getClass()) {
            return false;
        }
        return id.equals(((CategoryType) categoryType).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(CategoryType o) {
        return this.getCategory().compareToIgnoreCase(o.getCategory());
    }
}
