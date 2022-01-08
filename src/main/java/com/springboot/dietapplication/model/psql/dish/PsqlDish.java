package com.springboot.dietapplication.model.psql.dish;

import com.springboot.dietapplication.model.type.DishType;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "dishes")
public class PsqlDish implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "food_type_id")
    private Long foodTypeId;

    @Column(name = "menu_id")
    private Long menuId;

    @Column(name = "portions")
    private float portions;

    @Column(name = "recipe")
    private String recipe;

    public PsqlDish() {

    }

    public PsqlDish(DishType dishType) {
        if (dishType.getId() != null && !dishType.getId().isEmpty())
            this.id = Long.parseLong(dishType.getId());
        this.menuId = dishType.getMenuId();
        this.name = dishType.getName();
        this.portions = dishType.getPortions();
        this.recipe = dishType.getRecipe();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public float getPortions() {
        return portions;
    }

    public void setPortions(float portions) {
        this.portions = portions;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }
}
