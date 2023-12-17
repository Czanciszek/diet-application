package com.springboot.dietapplication.model.type;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuProductType {

    private String id;
    private CategoryType category;
    private String name;
    private float gramsOverall;

    public MenuProductType() {}
}
