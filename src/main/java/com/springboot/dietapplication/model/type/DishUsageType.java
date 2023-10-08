package com.springboot.dietapplication.model.type;

import com.springboot.dietapplication.model.psql.dish.PsqlDishUsage;

import java.io.Serial;
import java.io.Serializable;

public class DishUsageType implements Serializable {

    @Serial
    private static final long serialVersionUID = 5273753757462853686L;

    private final Long menuId;
    private final Long dishId;
    private final String startDate;
    private final String endDate;
    private final String dishName;
    private final Integer dishUsage;

    public DishUsageType(PsqlDishUsage psqlDishUsage) {
        this.menuId = psqlDishUsage.getMenuId();
        this.dishId = psqlDishUsage.getDishId();
        this.startDate = psqlDishUsage.getStartDate();
        this.endDate = psqlDishUsage.getEndDate();
        this.dishName = psqlDishUsage.getDishName();
        this.dishUsage = psqlDishUsage.getDishUsage();
    }

    public Long getMenuId() {
        return menuId;
    }

    public Long getDishId() {
        return dishId;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getDishName() {
        return dishName;
    }

    public Integer getDishUsage() {
        return dishUsage;
    }
}
