package com.springboot.dietapplication.model.type;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

@Getter
public class GenerateMenuType implements Serializable {

    @Serial
    private static final long serialVersionUID = -8763605596788667847L;

    private String menuId;

    private boolean showDates;

    private boolean generateRecipes;

    private boolean generateShoppingList;

    private String recommendations;

    public GenerateMenuType() {}

    public GenerateMenuType(String menuId, boolean showDates, boolean generateRecipes, boolean generateShoppingList, String recommendations) {
        this.menuId = menuId;
        this.showDates = showDates;
        this.generateRecipes = generateRecipes;
        this.generateShoppingList = generateShoppingList;
        this.recommendations = recommendations;
    }

}
