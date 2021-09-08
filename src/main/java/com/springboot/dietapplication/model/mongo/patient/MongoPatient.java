package com.springboot.dietapplication.model.mongo.patient;

import com.springboot.dietapplication.model.type.AllergensType;
import com.springboot.dietapplication.model.type.PatientType;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@Document(collection = "Patients")
public class MongoPatient {

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

    private List<MongoMeasurement> measurements;

    private Set<AllergensType> allergens;
    private Set<String> unlikelyCategories;

    public MongoPatient() {

    }

    public MongoPatient(PatientType patientType) {
        this.id = patientType.getId();
        this.name = patientType.getName();
        this.email = patientType.getEmail();
        this.numberPhone = patientType.getNumberPhone();
        this.birthDate = patientType.getBirthDate();
        this.sex = patientType.isSex();
        this.bodyHeight = patientType.getBodyHeight();
        this.changedLifestyleNote = patientType.getChangedLifestyleNote();
        this.currentLifestyleNote = patientType.getCurrentLifestyleNote();
        this.dietaryPurpose = patientType.getDietaryPurpose();
        this.allergens = patientType.getAllergens();
        this.unlikelyCategories = patientType.getUnlikelyCategories();
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

    public List<MongoMeasurement> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<MongoMeasurement> measurements) {
        this.measurements = measurements;
    }

    public Set<AllergensType> getAllergens() {
        return allergens;
    }

    public void setAllergens(Set<AllergensType> allergens) {
        this.allergens = allergens;
    }

    public Set<String> getUnlikelyCategories() {
        return unlikelyCategories;
    }

    public void setUnlikelyCategories(Set<String> unlikelyCategories) {
        this.unlikelyCategories = unlikelyCategories;
    }
}