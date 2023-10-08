package com.springboot.dietapplication.model.type;

import com.springboot.dietapplication.model.psql.menu.PsqlWeekMeal;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class WeekMealType implements Serializable {

    @Serial
    private static final long serialVersionUID = -103585575521933971L;

    private Long id;

    private Long menuId;

    private List<Long> dayMealList; // Lista odnośników do dziennych jadłospisów

    //private Map<String, Float> limits; // Zużyte kaloryczności

    public WeekMealType() {

    }

    public WeekMealType(Long menuId, List<Long> dayMealList) {
        this.menuId = menuId;
        this.dayMealList = dayMealList;
    }

    public WeekMealType(PsqlWeekMeal weekMeal) {
        this.id = weekMeal.getId();
        this.menuId = weekMeal.getMenuId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public List<Long> getDayMealList() {
        return dayMealList;
    }

    public void setDayMealList(List<Long> dayMealList) {
        this.dayMealList = dayMealList;
    }

}
