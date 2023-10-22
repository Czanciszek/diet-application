package com.springboot.dietapplication.model.type;

import java.util.Arrays;
import java.util.Optional;

public enum SexType {

    MALE(0),
    FEMALE(1),
    OTHER(2);

    private final long sexTypeValue;

    SexType(long sexTypeValue) {
        this.sexTypeValue = sexTypeValue;
    }

    public static Optional<SexType> valueOf(long sexTypeValue) {
        return Arrays.stream(values())
                .filter(sexType -> sexType.sexTypeValue == sexTypeValue)
                .findFirst();
    }

}
