package com.springboot.dietapplication.utils;

public enum RomanianNumber {
    I(1),
    II(2),
    III(3),
    IV(4),
    V(5),
    VI(6),
    VII(7),
    VIII(8),
    IX(9),
    X(10);

    public final int number;

    RomanianNumber(int i) {
        number = i;
    }

    public static String getRomanianValue(int value) {

        for (RomanianNumber number : RomanianNumber.values()) {
            if (number.number == value) {
                return "Tydzień " + number;
            }
        }

        return "Tydzień";
    }


}
