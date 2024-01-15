package com.springboot.dietapplication.model.type;

import com.springboot.dietapplication.model.psql.dish.PsqlDishUsage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class DishUsageType implements Serializable {

    @Serial
    private static final long serialVersionUID = 5273753757462853686L;

    private String menuId;
    private String dishId;
    private String startDate;
    private String endDate;
    private String dishName;
    private int dishUsage;

    public DishUsageType(PsqlDishUsage psqlDishUsage) {
        this.menuId = String.valueOf(psqlDishUsage.getMenuId());
        this.dishId = String.valueOf(psqlDishUsage.getDishId());
        this.startDate = psqlDishUsage.getStartDate();
        this.endDate = psqlDishUsage.getEndDate();
        this.dishName = psqlDishUsage.getDishName();
        this.dishUsage = psqlDishUsage.getDishUsage();
    }

}
