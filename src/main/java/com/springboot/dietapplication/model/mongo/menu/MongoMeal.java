package com.springboot.dietapplication.model.mongo.menu;

import com.springboot.dietapplication.model.mongo.dish.MongoDishProduct;
import com.springboot.dietapplication.model.psql.menu.PsqlDayMeal;
import com.springboot.dietapplication.model.type.FoodType;
import com.springboot.dietapplication.model.type.MealType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Setter
public class MongoMeal {

    @Id
    private String id;

    private String originDishId;
    private Boolean attachToRecipes;
    private Boolean isProduct;

    private String date;

    private String name;
    private String recipe;
    private Float portions;
    private Integer dishPortions;
    private Float grams;

    private FoodType foodType;
    private List<MongoDishProduct> products;
    private String creationDate;
    private String updateDate;
    private String deletionDate;

    public MongoMeal() {}

    public MongoMeal(MealType mealType, PsqlDayMeal psqlDayMeal) {
        this.id = mealType.getId();
        this.name = mealType.getName();
        this.recipe = mealType.getRecipe();
        this.portions = mealType.getPortions();
        this.dishPortions = (int) mealType.getDishPortions();
        this.grams = mealType.getGrams();
        this.foodType = mealType.getFoodType();
        this.isProduct = mealType.getIsProduct();
        this.originDishId = mealType.getBaseDishId();
        this.attachToRecipes = mealType.getOriginMealId() != null && mealType.getOriginMealId().equals(mealType.getId());
        this.products = mealType.getProductList()
                .stream()
                .map(MongoDishProduct::new)
                .collect(Collectors.toList());

        this.date = psqlDayMeal.getDate();
    }
}
