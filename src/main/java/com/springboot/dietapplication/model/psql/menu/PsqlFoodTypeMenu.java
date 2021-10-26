package com.springboot.dietapplication.model.psql.menu;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "food_types_menus")
public class PsqlFoodTypeMenu implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "food_type_id")
    private long foodTypeId;

    @Column(name = "menu_id")
    private long menuId;

    public PsqlFoodTypeMenu() {

    }

    public PsqlFoodTypeMenu(long foodTypeId, long menuId) {
        this.foodTypeId = foodTypeId;
        this.menuId = menuId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFoodTypeId() {
        return foodTypeId;
    }

    public void setFoodTypeId(long foodTypeId) {
        this.foodTypeId = foodTypeId;
    }

    public long getMenuId() {
        return menuId;
    }

    public void setMenuId(long menuId) {
        this.menuId = menuId;
    }
}