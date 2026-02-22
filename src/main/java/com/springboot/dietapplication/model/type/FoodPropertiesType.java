package com.springboot.dietapplication.model.type;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class FoodPropertiesType implements Serializable {

    @Serial
    private static final long serialVersionUID = 8958334201429822540L;

    @Id
    private Long id;
    private int energyValue;
    private float proteins;
    private float fats;
    private float saturatedFattyAcids;
    private float monoUnsaturatedFattyAcids;
    private float polyUnsaturatedFattyAcids;
    private float cholesterol;
    private float carbohydrates;
    private float sucrose;
    private float dietaryFibres;
    private float sodium;
    private float potassium;
    private float calcium;
    private float phosphorus;
    private float magnesium;
    private float iron;
    private float selenium;
    private float betaCarotene;
    private float vitaminD;
    private float vitaminC;

}
