package com.springboot.dietapplication.model.type;

import lombok.Getter;

@Getter
public enum EmployeeRoleType {

    OWNER("OWNER"),
    DIETITIAN("DIETITIAN"),
    HR("HR");

    public final String name;

    EmployeeRoleType(String name) {
        this.name = name;
    }

}
