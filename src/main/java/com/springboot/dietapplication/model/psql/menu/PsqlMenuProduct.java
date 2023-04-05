package com.springboot.dietapplication.model.psql.menu;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Comparator;

@Entity
@IdClass(PsqlMenuProductKey.class)
public class PsqlMenuProduct implements Serializable, Comparator<Long> {

    private static final long serialVersionUID = -4691174861219232915L;

    @Id
    @Column(name = "product_id")
    private Long productId;

    @Id
    @Column(name = "meal_id")
    private Long mealId;

    @Id
    @Column(name = "menu_id")
    private Long menuId;

    @Column(name = "week_meal_id")
    private Long weekMealId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "is_product")
    private boolean isProduct;

    @Column(name = "meal_grams")
    private float mealGrams;

    @Column(name = "meal_portions")
    private float mealPortions;

    @Column(name = "meal_name")
    private String mealName;

    @OneToOne
    @JoinColumn(name = "food_type_id")
    private PsqlFoodType foodType;

    @Column(name = "food_type_name")
    private String foodTypeName;

    @Column(name = "grams")
    private float grams;

    @Column(name = "amount")
    private float amount;

    @Column(name = "amount_type")
    private String amountType;

    @Column(name = "day_type")
    private String dayType;

    @Column(name = "date")
    private String date;

    public PsqlMenuProduct() {

    }

    public Long getProductId() {
        return productId;
    }

    public Long getMealId() {
        return mealId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public Long getWeekMealId() {
        return weekMealId;
    }

    public String getProductName() {
        return productName;
    }

    public boolean isProduct() {
        return isProduct;
    }

    public float getMealGrams() {
        return mealGrams;
    }

    public float getMealPortions() {
        return mealPortions;
    }

    public String getMealName() {
        return mealName;
    }

    public PsqlFoodType getFoodType() {
        return foodType;
    }

    public String getFoodTypeName() {
        return foodTypeName;
    }

    public float getGrams() {
        return grams;
    }

    public float getAmount() {
        return amount;
    }

    public String getAmountType() {
        return amountType;
    }

    public String getDayType() {
        return dayType;
    }

    public String getDate() {
        return date;
    }

    @Override
    public int compare(Long o1, Long o2) {
        return Long.compare(o2, o1);
    }
}

