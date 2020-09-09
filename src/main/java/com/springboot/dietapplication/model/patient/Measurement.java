package com.springboot.dietapplication.model.patient;

public class Measurement {

    private String measurementDate; //Data pomiaru

    private boolean sex; //Płeć //1 - Kobieta; 0 - Mężczyzna

    /**
     * Measurements for everyone
     */
    private float bodyWeight; //Masa ciała
    private float waist; //Talia
    private float abdominal; //Pas
    private float hips; //Biodra
    private float thighWidest; //Udo najszersze miejsce
    private float calf; //Łydka

    /**
     * Measurements only for women
     */
    private float breast; //Biust
    private float underBreast; //Pod biustem
    private float hipBones; //Kości biodrowe
    private float thighNarrowest; //Udo najwęższe

    /**
     * Measurements only for men
     */
    private float chest; //Klatka piersiowa
    private float arm; //Ramię

    /**
     * Constructor for women measurements
     */
    public Measurement(String measurementDate, boolean sex, float bodyWeight, float waist, float abdominal, float hips, float thighWidest, float calf, float breast, float underBreast, float hipBones, float thighNarrowest) {
        this.measurementDate = measurementDate;
        this.sex = sex;
        this.bodyWeight = bodyWeight;
        this.waist = waist;
        this.abdominal = abdominal;
        this.hips = hips;
        this.thighWidest = thighWidest;
        this.calf = calf;
        this.breast = breast;
        this.underBreast = underBreast;
        this.hipBones = hipBones;
        this.thighNarrowest = thighNarrowest;
    }

    /**
     * Constructor for men measurements
     */
    public Measurement(String measurementDate, boolean sex, float bodyWeight, float waist, float abdominal, float hips, float thighWidest, float calf, float chest, float arm) {
        this.measurementDate = measurementDate;
        this.sex = sex;
        this.bodyWeight = bodyWeight;
        this.waist = waist;
        this.abdominal = abdominal;
        this.hips = hips;
        this.thighWidest = thighWidest;
        this.calf = calf;
        this.chest = chest;
        this.arm = arm;
    }

    public String getMeasurementDate() {
        return measurementDate;
    }

    public void setMeasurementDate(String measurementDate) {
        this.measurementDate = measurementDate;
    }

    public boolean getSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
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