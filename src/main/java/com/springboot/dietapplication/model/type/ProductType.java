package com.springboot.dietapplication.model.type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springboot.dietapplication.model.mongo.product.MongoProduct;
import com.springboot.dietapplication.model.psql.product.PsqlProductFoodProperties;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.ListUtils;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
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

    public ProductType() {}

    public ProductType(PsqlProductFoodProperties psqlProduct) {
        this.id = String.valueOf(psqlProduct.getId());
        this.userId = String.valueOf(psqlProduct.getUserId());

        this.name = psqlProduct.getName();
        this.category = new CategoryType(psqlProduct);
    }

    public ProductType(MongoProduct mongoProduct) {
        this.id = mongoProduct.getId();
        this.userId = mongoProduct.getUserId();

        this.name = mongoProduct.getName();
        this.category = mongoProduct.getCategory();
        this.foodProperties = mongoProduct.getFoodProperties();
        this.amountTypes = ListUtils.emptyIfNull(mongoProduct.getAmountTypes());
        this.allergenTypes = ListUtils.emptyIfNull(mongoProduct.getAllergenTypes());
    }

}
