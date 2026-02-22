package com.springboot.dietapplication.model.type;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class MealMainSchemeCreateRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -632328395177909565L;

    private String patientId;
    private Integer order;
    private FoodType foodType;

    private SchemeBaseComponent proteinsBase;
    private SchemeBaseComponent fatsBase;
    private SchemeBaseComponent carbohydratesBase;
    private SchemeBaseComponent fibresBase;
}
