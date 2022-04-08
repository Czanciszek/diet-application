package com.springboot.dietapplication.model.type;

import com.springboot.dietapplication.model.psql.patient.PsqlMeasurement;

import java.io.Serializable;

public class MeasurementType implements Serializable {

    private static final long serialVersionUID = 5648470294571287883L;

    private Long id;
    private Long patientId;
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

    public MeasurementType(PsqlMeasurement measurement) {
        this.id = measurement.getId();
        this.patientId = measurement.getPatientId();
        this.measurementDate = measurement.getMeasurementDate();
        this.bodyWeight = measurement.getBodyWeight();
        this.breast = measurement.getBreast();
        this.underBreast = measurement.getUnderBreast();
        this.waist = measurement.getWaist();
        this.abdominal = measurement.getAbdominal();
        this.hipBones = measurement.getHipBones();
        this.hips = measurement.getHips();
        this.thighWidest = measurement.getThighWidest();
        this.thighNarrowest = measurement.getThighNarrowest();
        this.calf = measurement.getCalf();
        this.chest = measurement.getChest();
        this.arm = measurement.getArm();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getMeasurementDate() {
        return measurementDate;
    }

    public void setMeasurementDate(String measurementDate) {
        this.measurementDate = measurementDate;
    }

    public float getBodyWeight() {
        return bodyWeight;
    }

    public void setBodyWeight(float bodyWeight) {
        this.bodyWeight = bodyWeight;
    }

    public float getWaist() {
        return waist;
    }

    public void setWaist(float waist) {
        this.waist = waist;
    }

    public float getAbdominal() {
        return abdominal;
    }

    public void setAbdominal(float abdominal) {
        this.abdominal = abdominal;
    }

    public float getHips() {
        return hips;
    }

    public void setHips(float hips) {
        this.hips = hips;
    }

    public float getThighWidest() {
        return thighWidest;
    }

    public void setThighWidest(float thighWidest) {
        this.thighWidest = thighWidest;
    }

    public float getCalf() {
        return calf;
    }

    public void setCalf(float calf) {
        this.calf = calf;
    }

    public float getBreast() {
        return breast;
    }

    public void setBreast(float breast) {
        this.breast = breast;
    }

    public float getUnderBreast() {
        return underBreast;
    }

    public void setUnderBreast(float underBreast) {
        this.underBreast = underBreast;
    }

    public float getHipBones() {
        return hipBones;
    }

    public void setHipBones(float hipBones) {
        this.hipBones = hipBones;
    }

    public float getThighNarrowest() {
        return thighNarrowest;
    }

    public void setThighNarrowest(float thighNarrowest) {
        this.thighNarrowest = thighNarrowest;
    }

    public float getChest() {
        return chest;
    }

    public void setChest(float chest) {
        this.chest = chest;
    }

    public float getArm() {
        return arm;
    }

    public void setArm(float arm) {
        this.arm = arm;
    }
}