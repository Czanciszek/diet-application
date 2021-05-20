package com.springboot.dietapplication.helper;

import com.springboot.dietapplication.model.type.FoodPropertiesType;

public class FoodPropertiesHelper {

    public static FoodPropertiesType calculateBasicFoodProperties(int kcal) {
        FoodPropertiesType foodPropertiesType = new FoodPropertiesType();
        foodPropertiesType.setEnergyValue(kcal);

        float proteins = (0.1f * kcal) / 4.0f;
        foodPropertiesType.setProteins(proteins);

        float fats = (0.3f * kcal) / 9.0f;
        foodPropertiesType.setFats(fats);

        float carbohydrates = (0.6f * kcal) / 4.0f;
        foodPropertiesType.setCarbohydrates(carbohydrates);

        return foodPropertiesType;
    }
}
