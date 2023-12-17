package com.springboot.dietapplication.model.type;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class NewMenuType implements Serializable {

    @Serial
    private static final long serialVersionUID = -5136000565494491767L;

    private String id;

    private String startDate;

    private List<FoodType> foodTypes;

    private String patientId;

    private String recommendations;

    private int weekMenusCount;

    private float energyLimit;

    private float proteinsLimit;

    private float fatsLimit;

    private float carbohydratesLimit;

    public NewMenuType() {}

}
