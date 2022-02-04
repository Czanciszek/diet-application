package com.springboot.dietapplication.model.psql.dish;

import com.springboot.dietapplication.model.type.DishType;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "dishes")
public class PsqlDish implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "food_type_id")
    private Long foodTypeId;

    @Column(name = "portions")
    private float portions;

    @Column(name = "recipe")
    private String recipe;

    public PsqlDish() {

    }

    public PsqlDish(DishType dishType) {
        this.id = dishType.getId();
        this.name = dishType.getName();
        this.portions = dishType.getPortions();
        this.recipe = dishType.getRecipe();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
