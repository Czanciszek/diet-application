package com.springboot.dietapplication.model.type;

public class GenerateMenuType {

    private Long menuId;

    private boolean showDates;

    private String recommendations;

    public GenerateMenuType() {
    }

    public GenerateMenuType(Long menuId, boolean showDates, String recommendations) {
        this.menuId = menuId;
        this.showDates = showDates;
        this.recommendations = recommendations;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public boolean isShowDates() {
        return showDates;
    }

    public void setShowDates(boolean showDates) {
        this.showDates = showDates;
    }

    public String getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(String recommendations) {
        this.recommendations = recommendations;
    }
}
