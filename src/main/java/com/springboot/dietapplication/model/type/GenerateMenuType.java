package com.springboot.dietapplication.model.type;

public class GenerateMenuType {

    private Long menuId;

    private boolean showDates;

    private boolean generateRecipes;

    private boolean generateShoppingList;

    private String recommendations;

    public GenerateMenuType() {
    }

    public GenerateMenuType(Long menuId, boolean showDates, boolean generateRecipes, boolean generateShoppingList, String recommendations) {
        this.menuId = menuId;
        this.showDates = showDates;
        this.generateRecipes = generateRecipes;
        this.generateShoppingList = generateShoppingList;
        this.recommendations = recommendations;
    }

    public Long getMenuId() {
        return menuId;
    }

    public boolean isShowDates() {
        return showDates;
    }

    public boolean isGenerateRecipes() {
        return generateRecipes;
    }

    public boolean isGenerateShoppingList() {
        return generateShoppingList;
    }

    public String getRecommendations() {
        return recommendations;
    }
}
