package com.springboot.dietapplication.model.type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springboot.dietapplication.model.mongo.dish.MongoDishProduct;
import com.springboot.dietapplication.model.psql.dish.PsqlProductDish;
import com.springboot.dietapplication.model.psql.menu.PsqlProductMeal;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
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

    public ProductDishType() {}

    public ProductDishType(PsqlProductDish psqlProductDish) {
        this.productId = String.valueOf(psqlProductDish.getProductId());
        this.productName = psqlProductDish.getProductName();
        this.grams = psqlProductDish.getGrams();
        this.amount = psqlProductDish.getAmount();
    }

    public ProductDishType(PsqlProductMeal psqlProductMeal) {
        this.productId = String.valueOf(psqlProductMeal.getProductId());
        this.productName = psqlProductMeal.getProductName();
        this.grams = psqlProductMeal.getGrams();
        this.amount = psqlProductMeal.getAmount();
    }

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
