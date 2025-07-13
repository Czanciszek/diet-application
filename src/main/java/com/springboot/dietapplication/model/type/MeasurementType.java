package com.springboot.dietapplication.model.type;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class MeasurementType implements Serializable {

    @Serial
    private static final long serialVersionUID = 5648470294571287883L;

    private Long id;
    private String patientId;
    private String measurementDate; //Data pomiaru

    private float bodyWeight; //Masa ciała
    private float breast; // Biust - Only for women
    private float underBreast; //Pod biustem - Only for women
    private float waist; //Talia
    private float abdominal; //Pas
    private float hipBones; //Kości biodrowe - Only for women
    private float hips; //Biodra
    private float thighWidest; //Udo najszersze miejsce
    private float thighNarrowest; //Udo najwęższe - Only for women
    private float calf; //Łydka
    private float chest; //Klatka piersiowa - Only for men
    private float arm; //Ramię - Only for men

    public MeasurementType() {}


}