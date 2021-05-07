package com.springboot.dietapplication.model.type;

import com.springboot.dietapplication.model.patient.Measurement;
import com.springboot.dietapplication.model.patient.MongoPatient;
import com.springboot.dietapplication.model.psql.patient.PsqlPatient;

import java.util.List;

public class PatientType {

    private String id;
    private String name;
    private String birthDate;
    private String numberPhone;
    private String email;
    private boolean sex;
    private float bodyWeight;
    private int bodyHeight;

    //Private notes about patient
    private String currentLifestyleNote;
    private String changedLifestyleNote;
    private String dietaryPurpose;

    private List<Measurement> measurements;

    //odpowiedzi na wywiad
    //private InterviewAnswers answers;

    //lista jadłospisów
    //private List<Menu> menus;

    public PatientType() {

    }

    public PatientType(MongoPatient mongoPatient) {
        this.id = String.valueOf(mongoPatient.getId());
        this.name = mongoPatient.getName();
        this.email = mongoPatient.getEmail();
        this.birthDate = mongoPatient.getBirthDate();
        this.bodyHeight = mongoPatient.getBodyHeight();
        this.changedLifestyleNote = mongoPatient.getChangedLifestyleNote();
        this.currentLifestyleNote = mongoPatient.getCurrentLifestyleNote();
        this.dietaryPurpose = mongoPatient.getDietaryPurpose();
    }

    public PatientType(PsqlPatient psqlPatient) {
        this.id = String.valueOf(psqlPatient.getId());
        this.name = psqlPatient.getName();
        this.email = psqlPatient.getEmail();
        this.birthDate = psqlPatient.getBirthDate();
        this.bodyHeight = psqlPatient.getBodyHeight();
        this.changedLifestyleNote = psqlPatient.getChangedLifestyleNote();
        this.currentLifestyleNote = psqlPatient.getCurrentLifestyleNote();
        this.dietaryPurpose = psqlPatient.getDietaryPurpose();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isSex() {
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

    public int getBodyHeight() {
        return bodyHeight;
    }

    public void setBodyHeight(int bodyHeight) {
        this.bodyHeight = bodyHeight;
    }

    public String getCurrentLifestyleNote() {
        return currentLifestyleNote;
    }

    public void setCurrentLifestyleNote(String currentLifestyleNote) {
        this.currentLifestyleNote = currentLifestyleNote;
    }

    public String getChangedLifestyleNote() {
        return changedLifestyleNote;
    }

    public void setChangedLifestyleNote(String changedLifestyleNote) {
        this.changedLifestyleNote = changedLifestyleNote;
    }

    public String getDietaryPurpose() {
        return dietaryPurpose;
    }

    public void setDietaryPurpose(String dietaryPurpose) {
        this.dietaryPurpose = dietaryPurpose;
    }

    public List<Measurement> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<Measurement> measurements) {
        this.measurements = measurements;
    }

}
