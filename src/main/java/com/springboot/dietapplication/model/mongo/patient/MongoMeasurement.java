package com.springboot.dietapplication.model.mongo.patient;

import com.springboot.dietapplication.model.type.MeasurementType;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Measurements")
public class MongoMeasurement {

    private String id;
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

    public MongoMeasurement() {
    }

    /**
     * Constructor for women measurements
     */
    public MongoMeasurement(String patientId, String measurementDate, float bodyWeight, float waist, float abdominal, float hips, float thighWidest, float calf, float breast, float underBreast, float hipBones, float thighNarrowest) {
        this.patientId = patientId;
        this.measurementDate = measurementDate;
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
    public MongoMeasurement(String patientId, String measurementDate, float bodyWeight, float waist, float abdominal, float hips, float thighWidest, float calf, float chest, float arm) {
        this.patientId = patientId;
        this.measurementDate = measurementDate;
        this.bodyWeight = bodyWeight;
        this.waist = waist;
        this.abdominal = abdominal;
        this.hips = hips;
        this.thighWidest = thighWidest;
        this.calf = calf;
        this.chest = chest;
        this.arm = arm;
    }

    public MongoMeasurement(MeasurementType measurementType) {
        this.id = measurementType.getId();
        this.patientId = measurementType.getPatientId();
        this.measurementDate = measurementType.getMeasurementDate();
        this.bodyWeight = measurementType.getBodyWeight();
        this.breast = measurementType.getBreast();
        this.underBreast = measurementType.getUnderBreast();
        this.waist = measurementType.getWaist();
        this.abdominal = measurementType.getAbdominal();
        this.hipBones = measurementType.getHipBones();
        this.hips = measurementType.getHips();
        this.thighWidest = measurementType.getThighWidest();
        this.thighNarrowest = measurementType.getThighNarrowest();
        this.calf = measurementType.getCalf();
        this.chest = measurementType.getChest();
        this.arm = measurementType.getArm();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
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