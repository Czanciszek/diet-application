package com.springboot.dietapplication.model.type;

import lombok.Getter;

@Getter
public enum PatientRoleType {

    PARENT("PARENT"),
    CHILD("CHILD"),
    PARENT_CHILD("PARENT_CHILD"),
    PERSONAL("PERSONAL");

    public final String name;

    PatientRoleType(String name) {
        this.name = name;
    }

}
