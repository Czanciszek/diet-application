package com.springboot.dietapplication.model.psql.dish;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

@Entity
@IdClass(PsqlDishUsageKey.class)
public class PsqlDishUsage implements Serializable {

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

    public PsqlDishUsage(Long menuId, String startDate, String endDate, Long dishId, String dishName, Integer dishUsage) {
        this.menuId = menuId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dishId = dishId;
        this.dishName = dishName;
        this.dishUsage = dishUsage;
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
