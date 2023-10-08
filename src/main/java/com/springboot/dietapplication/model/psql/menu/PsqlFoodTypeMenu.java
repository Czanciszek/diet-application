package com.springboot.dietapplication.model.psql.menu;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "food_types_menus")
public class PsqlFoodTypeMenu implements Serializable {

    @Serial
    private static final long serialVersionUID = -6122707058926237958L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "food_type_id")
    private Long foodTypeId;

    @Column(name = "menu_id")
    private Long menuId;

    public PsqlFoodTypeMenu() {

    }

    public PsqlFoodTypeMenu(Long foodTypeId, Long menuId) {
        this.foodTypeId = foodTypeId;
        this.menuId = menuId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFoodTypeId() {
        return foodTypeId;
    }

    public void setFoodTypeId(Long foodTypeId) {
        this.foodTypeId = foodTypeId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }
}