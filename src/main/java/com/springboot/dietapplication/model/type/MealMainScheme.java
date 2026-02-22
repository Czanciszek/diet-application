package com.springboot.dietapplication.model.type;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class MealMainScheme implements Serializable {

    @Serial
    private static final long serialVersionUID = -5199959810382031968L;

    private String id;
    private Integer order;
    private FoodType foodType;
    private Float schemeCalories;

    private SchemeBaseComponent proteinsBaseComponent;
    private SchemeBaseComponent fatsBaseComponent;
    private SchemeBaseComponent carbohydratesBaseComponent;
    private SchemeBaseComponent fibresBaseComponent;

    public MealMainScheme(MongoMealMainScheme mealMainScheme) {
        this.id = mealMainScheme.getId();
        this.order = mealMainScheme.getOrder();
        this.foodType = mealMainScheme.getFoodType();
        this.schemeCalories = mealMainScheme.getSchemeCalories();

        if (mealMainScheme.getProteinsBaseComponent() != null) {
            this.proteinsBaseComponent = new SchemeBaseComponent(mealMainScheme.getProteinsBaseComponent());
        }

        if (mealMainScheme.getFatsBaseComponent() != null) {
            this.fatsBaseComponent = new SchemeBaseComponent(mealMainScheme.getFatsBaseComponent());
        }

        if (mealMainScheme.getCarbohydratesBaseComponent() != null) {
            this.carbohydratesBaseComponent = new SchemeBaseComponent(mealMainScheme.getCarbohydratesBaseComponent());
        }

        if (mealMainScheme.getFibresBaseComponent() != null) {
            this.fibresBaseComponent = new SchemeBaseComponent(mealMainScheme.getFibresBaseComponent());
        }
    }
}
