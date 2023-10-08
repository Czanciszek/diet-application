package com.springboot.dietapplication.model.type;

import com.springboot.dietapplication.model.psql.menu.PsqlDayMeal;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class DayMealType implements Serializable {

    @Serial
    private static final long serialVersionUID = 7516939376329349749L;

    private Long id;

    private Long weekMealId;

    private List<Long> mealList; // Lista odnośników do posiłków

    private DayType dayType; //Day name

    private String date;

    public DayMealType() {

    }

    public DayMealType(Long weekMealId, List<Long> mealList, DayType dayType, String date) {
        this.weekMealId = weekMealId;
        this.mealList = mealList;
        this.dayType = dayType;
        this.date = date;
    }

    public DayMealType(PsqlDayMeal dayMeal) {
        this.id = dayMeal.getId();
        this.weekMealId = dayMeal.getWeekMealId();
        this.date = dayMeal.getDate();
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

    public List<Long> getMealList() {
        return mealList;
    }

    public void setMealList(List<Long> mealList) {
        this.mealList = mealList;
    }

    public DayType getDayType() {
        return dayType;
    }

    public void setDayType(DayType dayType) {
        this.dayType = dayType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
