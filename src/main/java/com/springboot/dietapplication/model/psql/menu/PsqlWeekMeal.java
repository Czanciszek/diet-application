package com.springboot.dietapplication.model.psql.menu;

import com.springboot.dietapplication.model.type.WeekMealType;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "week_meals")
public class PsqlWeekMeal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "menu_id")
    private long menuId;

    public PsqlWeekMeal() {

    }

    public PsqlWeekMeal(WeekMealType weekMealType) {
        if (weekMealType.getId() != null && !weekMealType.getId().isEmpty())
            this.id = Long.parseLong(weekMealType.getId());
        if (weekMealType.getMenuId() != null && !weekMealType.getMenuId().isEmpty())
            this.menuId = Long.parseLong(weekMealType.getMenuId());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMenuId() {
        return menuId;
    }

    public void setMenuId(long menuId) {
        this.menuId = menuId;
    }
}
