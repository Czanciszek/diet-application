package com.springboot.dietapplication.model.type;

public enum UserType {

    ADMIN("ADMIN"),
    USER("USER");

    public final String name;

    UserType(String name) {
        this.name = name;
    }

    public String getUserType() {
        return this.name;
    }
}
