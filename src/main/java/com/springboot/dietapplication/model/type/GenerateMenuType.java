package com.springboot.dietapplication.model.type;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Getter
@NoArgsConstructor
@SuppressWarnings({"UnusedDeclaration"})
public class GenerateMenuType implements Serializable {

    @Serial
    private static final long serialVersionUID = -8763605596788667847L;

    private String menuId;

    private boolean showDates;

    private boolean generateRecipes;

    private boolean generateShoppingList;

    private String recommendations;

}
