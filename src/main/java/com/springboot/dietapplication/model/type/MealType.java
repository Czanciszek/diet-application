package com.springboot.dietapplication.model.type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springboot.dietapplication.model.mongo.menu.MongoMeal;
import com.springboot.dietapplication.model.psql.menu.PsqlMeal;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class MealType implements Serializable, Comparable<MealType> {

    @Serial
    private static final long serialVersionUID = -3419782569024875357L;

    private String id;

    private String name;

    private boolean attachToRecipes; // Decides whenever meal should be added to recipes list

    private String originDishId; // Reference to selected dish

    private Boolean isProduct; // true - for Product; false - for Dish

    private List<ProductDishType> productList; // Product list for dish type

    private float portions; // Portions of dish on meal

    private float grams; // Portions of dish on meal

    private float dishPortions; // Portions for full dish recipe (portions from DishType)

    private String recipe; // Recipe for dish meal

    private FoodType foodType; //Rodzaj posiłku

    @JsonIgnore
    private String dayMealId; // Reference to day

    public MealType() {}

    public MealType(MealType meal) {
        this.id = meal.getId();
        this.name = meal.getName();
        this.dayMealId = meal.getDayMealId();
        this.attachToRecipes = meal.isAttachToRecipes();
        this.originDishId = meal.getOriginDishId();
        this.isProduct = meal.getIsProduct();
        this.productList = meal.getProductList();
        this.portions = meal.getPortions();
        this.grams = meal.getGrams();
        this.dishPortions = meal.getDishPortions();
        this.recipe = meal.getRecipe();
        this.foodType = meal.getFoodType();
    }

    public MealType(PsqlMeal meal) {
        this.id = String.valueOf(meal.getId());
        this.dayMealId = String.valueOf(meal.getDayMealId());
        this.attachToRecipes = meal.getOriginMealId() != null && meal.getOriginMealId().equals(meal.getId());
        this.originDishId = meal.getBaseDishId() != null ? String.valueOf(meal.getBaseDishId()) : null;
        this.name = meal.getName();
        this.isProduct = meal.isProduct();
        this.portions = meal.getPortions();
        this.grams = meal.getGrams();
        this.dishPortions = meal.getDishPortions();
        this.recipe = meal.getRecipe();
    }

    public MealType(MongoMeal mongoMeal) {
        this.id = mongoMeal.getId();
        this.name = mongoMeal.getName();
        this.attachToRecipes = mongoMeal.getAttachToRecipes();
        this.originDishId = mongoMeal.getOriginDishId();
        this.isProduct = mongoMeal.getIsProduct();
        this.portions = mongoMeal.getPortions();
        this.dishPortions = mongoMeal.getDishPortions();
        this.grams = mongoMeal.getGrams();
        this.recipe = mongoMeal.getRecipe();
        this.foodType = mongoMeal.getFoodType();

        this.productList = mongoMeal.getProducts()
                .stream()
                .map(ProductDishType::new)
                .collect(Collectors.toList());
    }

    @Override
    public int compareTo(MealType o) {
        return this.getName().trim().compareToIgnoreCase(o.getName().trim());
    }
}
