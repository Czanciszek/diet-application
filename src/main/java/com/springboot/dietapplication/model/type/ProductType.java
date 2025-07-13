package com.springboot.dietapplication.model.type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springboot.dietapplication.model.mongo.product.MongoProduct;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.collections4.ListUtils;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductType implements Serializable {

    @Serial
    private static final long serialVersionUID = -6504989715893867042L;

    private String id;

    @JsonIgnore
    private String userId;

    private String name;
    private CategoryType category;
    private FoodPropertiesType foodProperties;
    private List<ProductAmountType> amountTypes;
    private List<AllergenType> allergenTypes;
    private ProductReplacements replacements;

    public ProductType(MongoProduct mongoProduct) {
        this.id = mongoProduct.getId();
        this.userId = mongoProduct.getUserId();

        this.name = mongoProduct.getName();
        this.category = mongoProduct.getCategory();
        this.foodProperties = mongoProduct.getFoodProperties();
        this.amountTypes = ListUtils.emptyIfNull(mongoProduct.getAmountTypes());
        this.allergenTypes = ListUtils.emptyIfNull(mongoProduct.getAllergenTypes());
        this.replacements = mongoProduct.getReplacements();
    }

}
