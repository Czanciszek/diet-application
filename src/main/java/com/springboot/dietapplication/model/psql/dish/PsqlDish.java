package com.springboot.dietapplication.model.psql.dish;

import com.springboot.dietapplication.model.type.DishType;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "dishes")
public class PsqlDish implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "meal_type_id")
    private Long mealTypeId;

    @Column(name = "portions")
    private float portions;

    @Column(name = "recipe")
    private String recipe;

    public PsqlDish() {

    }

    public PsqlDish(DishType dishType) {
        if (dishType.getId() != null && !dishType.getId().isEmpty())
            this.id = Long.parseLong(dishType.getId());
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

    public Long getMealTypeId() {
        return mealTypeId;
    }

    public void setMealTypeId(Long mealTypeId) {
        this.mealTypeId = mealTypeId;
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
