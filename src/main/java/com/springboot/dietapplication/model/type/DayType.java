package com.springboot.dietapplication.model.type;

import java.util.Arrays;
import java.util.Optional;

public enum DayType {
    MONDAY(1),
    TUESDAY(2),
    WEDNESDAY(3),
    THURSDAY(4),
    FRIDAY(5),
    SATURDAY(6),
    SUNDAY(7);

    private final int dayTypeValue;

    DayType(int dayTypeValue) {
        this.dayTypeValue = dayTypeValue;
    }

    public static Optional<DayType> valueOf(int dayTypeValue) {
        return Arrays.stream(values())
                .filter(dayType -> dayType.dayTypeValue == dayTypeValue)
                .findFirst();
    }
}
