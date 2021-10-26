package com.springboot.dietapplication.model.psql.menu;

import com.springboot.dietapplication.model.type.DayMealType;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "day_meals")
public class PsqlDayMeal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "week_meal_id")
    private long weekMealId;

    @Column(name = "day_type")
    private String dayType;

    @Column(name = "date")
    private String date;

    public PsqlDayMeal() {

    }

    public PsqlDayMeal(DayMealType dayMealType) {
        if (dayMealType.getId() != null && !dayMealType.getId().isEmpty())
            this.id = Long.parseLong(dayMealType.getId());
        if (dayMealType.getWeekMealId() != null && !dayMealType.getWeekMealId().isEmpty())
        this.weekMealId = Long.parseLong(dayMealType.getWeekMealId());
        this.dayType = dayMealType.getDayType().toString();
        this.date = dayMealType.getDate();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getWeekMealId() {
        return weekMealId;
    }

    public void setWeekMealId(long weekMealId) {
        this.weekMealId = weekMealId;
    }

    public String getDayType() {
        return dayType;
    }

    public void setDayType(String dayType) {
        this.dayType = dayType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}