package com.springboot.dietapplication.model.psql.menu;

import com.springboot.dietapplication.model.type.MealType;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "meal_types_menus")
public class PsqlMealTypeMenu implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "meal_type_id")
    private long mealTypeId;

    @Column(name = "menu_id")
    private long menuId;

    public PsqlMealTypeMenu() {

    }

    public PsqlMealTypeMenu(long mealTypeId, long menuId) {
        this.mealTypeId = mealTypeId;
        this.menuId = menuId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMealTypeId() {
        return mealTypeId;
    }

    public void setMealTypeId(long mealTypeId) {
        this.mealTypeId = mealTypeId;
    }

    public long getMenuId() {
        return menuId;
    }

    public void setMenuId(long menuId) {
        this.menuId = menuId;
    }
}