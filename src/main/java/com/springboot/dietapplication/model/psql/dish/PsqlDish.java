package com.springboot.dietapplication.model.psql.dish;

import com.springboot.dietapplication.model.type.DishType;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Deprecated(since = "0.1.0", forRemoval = true)
@Entity
@Table(name = "dishes")
public class PsqlDish implements Serializable {

    @Serial
    private static final long serialVersionUID = 7593811047225371609L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

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
        this.id = Long.valueOf(dishType.getId());
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
