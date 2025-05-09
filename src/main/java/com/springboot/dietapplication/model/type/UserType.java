package com.springboot.dietapplication.model.type;

import lombok.Getter;

@Getter
public enum UserType {

    ADMIN("ADMIN"),
    USER("USER");

    public final String name;

    UserType(String name) {
        this.name = name;
    }

}
