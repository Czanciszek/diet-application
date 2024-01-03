package com.springboot.dietapplication.model.mongo.menu;

import com.springboot.dietapplication.model.mongo.dish.MongoDishProduct;
import com.springboot.dietapplication.model.type.FoodType;
import com.springboot.dietapplication.model.type.MealType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.UUID;
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

    public MongoMeal(MongoMeal mongoMeal, String currentDate) {
        this.id = UUID.randomUUID().toString();
        this.attachToRecipes = false;
        this.creationDate = currentDate;
        this.updateDate = currentDate;

        this.name = mongoMeal.getName();
        this.recipe = mongoMeal.getRecipe();
        this.portions = mongoMeal.getPortions();
        this.dishPortions = mongoMeal.getDishPortions();
        this.grams = mongoMeal.getGrams();
        this.foodType = mongoMeal.getFoodType();
        this.isProduct = mongoMeal.getIsProduct();
        this.originDishId = mongoMeal.getOriginDishId();
        this.products = mongoMeal.getProducts()
                .stream()
                .map(MongoDishProduct::new)
                .collect(Collectors.toList());
        this.date = mongoMeal.getDate();
        this.deletionDate = mongoMeal.getDeletionDate();
    }

    public MongoMeal(MealType mealType) {
        this.id = mealType.getId();
        this.name = mealType.getName();
        this.recipe = mealType.getRecipe();
        this.portions = mealType.getPortions();
        this.dishPortions = (int) mealType.getDishPortions();
        this.grams = mealType.getGrams();
        this.foodType = mealType.getFoodType();
        this.isProduct = mealType.getIsProduct();
        this.originDishId = mealType.getOriginDishId();
        this.attachToRecipes = mealType.isAttachToRecipes();
        this.products = mealType.getProductList()
                .stream()
                .map(MongoDishProduct::new)
                .collect(Collectors.toList());
    }

    public void updateFrom(MealType mealType) {
        setName(mealType.getName());
        setRecipe(mealType.getRecipe());
        setPortions(mealType.getPortions());
        setDishPortions((int) mealType.getDishPortions());
        setGrams(mealType.getGrams());
        setFoodType(mealType.getFoodType());
        setIsProduct(mealType.getIsProduct());
        setOriginDishId(mealType.getOriginDishId());
        setAttachToRecipes(mealType.isAttachToRecipes());
        setProducts(mealType.getProductList()
                .stream()
                .map(MongoDishProduct::new)
                .collect(Collectors.toList()));
    }
}
