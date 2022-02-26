package com.springboot.dietapplication.model.psql.menu;

import com.springboot.dietapplication.model.type.DayMealType;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "day_meals")
public class PsqlDayMeal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "week_meal_id")
    private Long weekMealId;

    @Column(name = "day_type")
    private String dayType;

    @Column(name = "date")
    private String date;

    public PsqlDayMeal() {

    }

    public PsqlDayMeal(DayMealType dayMealType) {
        this.id = dayMealType.getId();
        this.weekMealId = dayMealType.getWeekMealId();
        this.dayType = dayMealType.getDayType().toString();
        this.date = dayMealType.getDate();
    }

    public PsqlDayMeal(PsqlDayMeal psqlDayMeal) {
        this.dayType = psqlDayMeal.getDayType();
        this.date = psqlDayMeal.getDate();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWeekMealId() {
        return weekMealId;
    }

    public void setWeekMealId(Long weekMealId) {
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