package com.springboot.dietapplication.model.patient;

import com.springboot.dietapplication.model.base.BaseDoc;
import com.springboot.dietapplication.model.base.DocRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Measurements")
public class Measurement extends BaseDoc {

    private DocRef<Patient> patientDocRef;
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

    public Measurement() {
    }

    /**
     * Constructor for women measurements
     */
    public Measurement(DocRef<Patient> patientDocRef, String measurementDate, float bodyWeight, float waist, float abdominal, float hips, float thighWidest, float calf, float breast, float underBreast, float hipBones, float thighNarrowest) {
        this.patientDocRef = patientDocRef;
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
    public Measurement(DocRef<Patient> patientDocRef, String measurementDate, float bodyWeight, float waist, float abdominal, float hips, float thighWidest, float calf, float chest, float arm) {
        this.patientDocRef = patientDocRef;
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

    public DocRef<Patient> getPatientDocRef() {
        return patientDocRef;
    }

    public void setPatientDocRef(DocRef<Patient> patientDocRef) {
        this.patientDocRef = patientDocRef;
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