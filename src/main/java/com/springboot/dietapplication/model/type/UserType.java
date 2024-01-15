package com.springboot.dietapplication.model.type;

import lombok.Getter;

@Getter
public enum UserType {

    ADMIN("ADMIN"),
    DIETITIAN("DIETITIAN"),
    PATIENT("PATIENT");

    public final String name;

    UserType(String name) {
        this.name = name;
    }

}
