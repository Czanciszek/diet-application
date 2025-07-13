package com.springboot.dietapplication.model.type.enums;

import java.util.Arrays;
import java.util.Optional;

public enum AllergenType {

    GLUTEN(1), // Gluten
    LACTOSE(2), // Laktoza
    STARCH(3),// Skrobia
    EGGS(4), // Jaja
    FISH(5), // Ryby
    NUTS(6), // Orzechy
    PEANUTS(7), // Orzechy arachidowe
    SOY(8), // Soja
    CELERY(9), // Seler
    MUSTARD(10), //Gorczyca
    SESAME(11), // Sezam
    SULPHUR_DIOXIDE(12), // Dwutlenek siarki
    LUPINE(13), // Łupin
    MOLLUSCS(14); // Mięczaki

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
