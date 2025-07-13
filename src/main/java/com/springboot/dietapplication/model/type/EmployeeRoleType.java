package com.springboot.dietapplication.model.type;

import lombok.Getter;

@Getter
public enum EmployeeRoleType {

    OWNER("OWNER"),
    CLINICIAN("CLINICIAN"),
    HUMAN_RESOURCES("HUMAN_RESOURCES");

    public final String name;

    EmployeeRoleType(String name) {
        this.name = name;
    }

}
