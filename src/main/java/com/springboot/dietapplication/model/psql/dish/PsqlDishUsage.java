package com.springboot.dietapplication.model.psql.dish;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import java.io.Serial;
import java.io.Serializable;

@Deprecated(since = "0.1.0", forRemoval = true)
@Entity
@IdClass(PsqlDishUsageKey.class)
public class PsqlDishUsage implements Serializable {

    @Serial
    private static final long serialVersionUID = 9108960675914350086L;

    @Id
    @Column(name = "menu_id")
    private Long menuId;

    @Id
    @Column(name = "dish_id")
    private Long dishId;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "end_date")
    private String endDate;

    @Column(name = "dish_name")
    private String dishName;

    @Column(name = "dish_usage")
    private Integer dishUsage;


    public PsqlDishUsage() {
    }

    public Long getMenuId() {
        return menuId;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public Long getDishId() {
        return dishId;
    }

    public String getDishName() {
        return dishName;
    }

    public Integer getDishUsage() {
        return dishUsage;
    }
}
