package com.springboot.dietapplication.model.mongo;

import com.springboot.dietapplication.model.type.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.ListUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Document("products")
public class MongoProduct {

    @Id
    private String id;

    private String userId;

    private String name;
    private CategoryType category;
    private String creationDate;
    private String updateDate;
    private String deletionDate;

    private FoodPropertiesType foodProperties;
    private List<ProductAmountType> amountTypes;
    private List<AllergenType> allergenTypes = new ArrayList<>();

    public MongoProduct() { }

    public MongoProduct(ProductType productType) {
        this.id = productType.getId();
        this.userId = productType.getUserId();

        this.name = productType.getName();
        this.category = productType.getCategory();
        this.foodProperties = productType.getFoodProperties();
        this.amountTypes = ListUtils.emptyIfNull(productType.getAmountTypes());
        this.allergenTypes = ListUtils.emptyIfNull(productType.getAllergenTypes());
    }

}
