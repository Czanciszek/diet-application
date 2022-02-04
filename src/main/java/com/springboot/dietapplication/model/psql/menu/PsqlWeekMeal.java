package com.springboot.dietapplication.model.psql.menu;

import com.springboot.dietapplication.model.type.WeekMealType;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "week_meals")
public class PsqlWeekMeal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "menu_id")
    private Long menuId;

    public PsqlWeekMeal() {

    }

    public PsqlWeekMeal(WeekMealType weekMealType) {
        this.id = weekMealType.getId();
        this.menuId = weekMealType.getMenuId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }
}
