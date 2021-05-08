package com.springboot.dietapplication.model.psql.patient;

import com.springboot.dietapplication.model.type.MeasurementType;

import javax.persistence.*;

@Entity
@Table(name = "measurements")
public class PsqlMeasurement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "patient_id")
    private Long patientId;

    @Column(name = "date")
    private String measurementDate; //Data pomiaru

    @Column(name = "body_weight")
    private float bodyWeight; //Masa ciała

    @Column(name = "breast")
    private float breast; // Biust - Only for women

    @Column(name = "under_breast")
    private float underBreast; //Pod biustem - Only for women

    @Column(name = "waist")
    private float waist; //Talia

    @Column(name = "abdominal")
    private float abdominal; //Pas

    @Column(name = "hip_bones")
    private float hipBones; //Kości biodrowe - Only for women

    @Column(name = "hips")
    private float hips; //Biodra

    @Column(name = "thigh_widest")
    private float thighWidest; //Udo najszersze miejsce

    @Column(name = "thigh_narrowest")
    private float thighNarrowest; //Udo najwęższe - Only for women

    @Column(name = "calf")
    private float calf; //Łydka

    @Column(name = "chest")
    private float chest; //Klatka piersiowa - Only for men

    @Column(name = "arm")
    private float arm; //Ramię - Only for men

    public PsqlMeasurement() {

    }

    public PsqlMeasurement(MeasurementType measurementType) {
        if (measurementType.getId() != null && !measurementType.getId().isEmpty())
            this.id = Long.parseLong(measurementType.getId());
        if (measurementType.getPatientId() != null && !measurementType.getPatientId().isEmpty())
            this.patientId = Long.parseLong(measurementType.getPatientId());
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

    public float getHipBones() {
        return hipBones;
    }

    public void setHipBones(float hipBones) {
        this.hipBones = hipBones;
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

    public float getThighNarrowest() {
        return thighNarrowest;
    }

    public void setThighNarrowest(float thighNarrowest) {
        this.thighNarrowest = thighNarrowest;
    }

    public float getCalf() {
        return calf;
    }

    public void setCalf(float calf) {
        this.calf = calf;
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
