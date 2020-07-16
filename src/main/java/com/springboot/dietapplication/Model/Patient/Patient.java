package com.springboot.dietapplication.Model.Patient;

import com.springboot.dietapplication.Model.Base.BaseDoc;

import java.util.List;

public class Patient extends BaseDoc {

    private String birthDate;
    private String numberPhone;
    private String email;
    private float bodyWeight;
    private int bodyHeight;

    //Private notes about patient
    private String currentLifestyleNote;
    private String changedLifestyleNote;
    private String dietaryPurpose;

    private List<Measurement> measurements;

    //odpowiedzi na wywiad
    //private InterviewAnswers answers;

    //lista diet
    //private List<Diet> diets;


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