package com.springboot.dietapplication.model.type;

import java.util.Arrays;
import java.util.Optional;

public enum AmountType {

    NONE(0), // Null
    GLASS(1), // Szklanka
    SPOON(2), // Łyżka
    TEASPOON(3), // Łyżeczka
    PIECE(4), // Sztuka
    SLICE(5), // Plaster
    LEAF(6), // Lisć
    HANDFUL(7), // Garść
    SCOOP(8); // Miarka

    private final long amountTypeValue;

    AmountType(long amountTypeValue) {
        this.amountTypeValue = amountTypeValue;
    }

    public static Optional<AmountType> valueOf(long amountTypeValue) {
        return Arrays.stream(values())
                .filter(amountType -> amountType.amountTypeValue == amountTypeValue)
                .findFirst();
    }

}
