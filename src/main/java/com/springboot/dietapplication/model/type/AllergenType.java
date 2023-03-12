package com.springboot.dietapplication.model.type;

import java.util.Arrays;
import java.util.Optional;

public enum AllergenType {

    GLUTEN(1), // Gluten
    LACTOSE(2), // Laktoza
    STARCH(3); //Skrobia

    private final long allergenTypeValue;

    AllergenType(long allergenTypeValue) {
        this.allergenTypeValue = allergenTypeValue;
    }

    public static Optional<AllergenType> valueOf(long allergenTypeValue) {
        return Arrays.stream(values())
                .filter(amountType -> amountType.allergenTypeValue == allergenTypeValue)
                .findFirst();
    }

}
