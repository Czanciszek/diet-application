package com.springboot.dietapplication.model.type;

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
public class MealType implements Serializable {

    @Serial
    private static final long serialVersionUID = -3419782569024875357L;

    private String id;

    private String name;

    private String dayMealId; // Reference to day

    private String originMealId; // Reference to origin meal day

    private String baseDishId; // Reference to selected dish

    private Boolean isProduct; // true - for Product; false - for Dish

    private List<ProductDishType> productList; // Product list for dish type

    private float portions; // Portions of dish on meal

    private float grams; // Portions of dish on meal

    private float dishPortions; // Portions for full dish recipe (portions from DishType)

    private String recipe; // Recipe for dish meal

    private FoodType foodType; //Rodzaj posiłku

    public MealType() {}

    public MealType(MealType meal) {
        this.name = meal.name;
        this.dayMealId = meal.dayMealId;
        this.originMealId = meal.originMealId;
        this.baseDishId = meal.baseDishId;
        this.isProduct = meal.isProduct;
        this.productList = meal.productList;
        this.portions = meal.portions;
        this.grams = meal.grams;
        this.dishPortions = meal.dishPortions;
        this.recipe = meal.recipe;
        this.foodType = meal.foodType;
    }

    public MealType(PsqlMeal meal) {
        this.id = String.valueOf(meal.getId());
        this.dayMealId = String.valueOf(meal.getDayMealId());
        this.originMealId = meal.getBaseDishId() != null ? String.valueOf(meal.getOriginMealId()) : null;
        this.baseDishId = meal.getBaseDishId() != null ? String.valueOf(meal.getBaseDishId()) : null;
        this.name = meal.getName();
        this.isProduct = meal.isProduct();
        this.portions = meal.getPortions();
        this.grams = meal.getGrams();
        this.dishPortions = meal.getDishPortions();
        this.recipe = meal.getRecipe();
    }

    public MealType(MongoMeal mongoMeal) {
        this.name = mongoMeal.getName();
        this.originMealId = mongoMeal.getAttachToRecipes() ? mongoMeal.getId() : null;
        this.baseDishId = mongoMeal.getOriginDishId();
        this.isProduct = mongoMeal.getIsProduct();
        this.portions = mongoMeal.getPortions();
        this.dishPortions = mongoMeal.getDishPortions();
        this.grams = mongoMeal.getGrams();
        this.recipe = mongoMeal.getRecipe();
        this.foodType = mongoMeal.getFoodType();

        this.productList = mongoMeal.getProducts()
                .stream()
                .map(ProductDishType::new)
                .collect(Collectors.toList());;
    }

}
