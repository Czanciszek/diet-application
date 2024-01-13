package com.springboot.dietapplication.model.type;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.Optional;

@Deprecated(since = "0.1.0", forRemoval = true)
public enum DayType {
    MONDAY(1),
    TUESDAY(2),
    WEDNESDAY(3),
    THURSDAY(4),
    FRIDAY(5),
    SATURDAY(6),
    SUNDAY(0);

    private final int dayTypeValue;

    DayType(int dayTypeValue) {
        this.dayTypeValue = dayTypeValue;
    }

    public static Optional<DayType> valueOf(int dayTypeValue) {
        return Arrays.stream(values())
                .filter(dayType -> dayType.dayTypeValue == dayTypeValue)
                .findFirst();
    }

    public static DayType from(DayOfWeek dayOfWeek) {
        switch (dayOfWeek) {
            case MONDAY -> { return MONDAY; }
            case TUESDAY -> { return TUESDAY; }
            case WEDNESDAY -> { return WEDNESDAY; }
            case THURSDAY -> { return THURSDAY; }
            case FRIDAY -> { return FRIDAY; }
            case SATURDAY -> { return SATURDAY; }
            case SUNDAY -> { return SUNDAY; }
            default -> throw new IllegalStateException("Unexpected value: " + dayOfWeek);
        }
    }
}
