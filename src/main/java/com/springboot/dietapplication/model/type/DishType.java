package com.springboot.dietapplication.model.type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springboot.dietapplication.model.mongo.dish.MongoDish;
import com.springboot.dietapplication.model.psql.dish.PsqlDish;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class DishType implements Serializable {

    @Serial
    private static final long serialVersionUID = 7119598963939561319L;

    private String id;

    @JsonIgnore
    private String userId;

    private String name;
    private List<ProductDishType> products;
    private FoodType foodType;
    private float portions;
    private String recipe;

    public DishType() {}

    public DishType(PsqlDish dish) {
        this.id = String.valueOf(dish.getId());
        this.userId = String.valueOf(dish.getUserId());
        this.name = dish.getName();
        this.portions = dish.getPortions();
        this.recipe = dish.getRecipe();
    }

    public DishType(MongoDish mongoDish) {
        this.id = mongoDish.getId();
        this.userId = mongoDish.getUserId();

        this.name = mongoDish.getName();
        this.foodType = mongoDish.getFoodTypes().get(0);
        this.portions = mongoDish.getPortions();
        this.recipe = mongoDish.getRecipe();

        this.products = mongoDish.getProducts()
                .stream()
                .map(ProductDishType::new)
                .collect(Collectors.toList());
    }

}
