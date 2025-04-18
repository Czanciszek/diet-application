package com.springboot.dietapplication.model.type;

import lombok.Getter;

@Getter
public enum PatientRoleType {

    PARENT("ADMIN"),
    CHILD("USER"),
    ADULT("ADULT");

    public final String name;

    PatientRoleType(String name) {
        this.name = name;
    }

}
