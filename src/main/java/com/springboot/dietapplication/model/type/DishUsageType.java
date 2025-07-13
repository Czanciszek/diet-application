package com.springboot.dietapplication.model.type;

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

}
