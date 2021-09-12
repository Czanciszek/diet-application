package com.springboot.dietapplication.model.type;

import com.springboot.dietapplication.model.mongo.patient.MongoMeasurement;
import com.springboot.dietapplication.model.mongo.patient.MongoPatient;
import com.springboot.dietapplication.model.psql.patient.PsqlPatient;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PatientType {

    private String id;
    private String name;
    private String birthDate;
    private String numberPhone;
    private String email;
    private boolean sex;
    private int bodyHeight;

    //Private notes about patient
    private String currentLifestyleNote;
    private String changedLifestyleNote;
    private String dietaryPurpose;

    private List<MongoMeasurement> measurements;

    private Set<AllergensType> allergens;
    private Set<String> unlikelyCategories;

    public PatientType() {

    }

    public PatientType(MongoPatient mongoPatient) {
        this.id = String.valueOf(mongoPatient.getId());
        this.name = mongoPatient.getName();
        this.email = mongoPatient.getEmail();
        this.numberPhone = mongoPatient.getNumberPhone();
        this.birthDate = mongoPatient.getBirthDate();
        this.sex = mongoPatient.isSex();
        this.bodyHeight = mongoPatient.getBodyHeight();
        this.changedLifestyleNote = mongoPatient.getChangedLifestyleNote();
        this.currentLifestyleNote = mongoPatient.getCurrentLifestyleNote();
        this.dietaryPurpose = mongoPatient.getDietaryPurpose();
        this.allergens = mongoPatient.getAllergens();
        this.unlikelyCategories = mongoPatient.getUnlikelyCategories();
    }

    public PatientType(PsqlPatient psqlPatient) {
        this.id = String.valueOf(psqlPatient.getId());
        this.name = psqlPatient.getName();
        this.email = psqlPatient.getEmail();
        this.numberPhone = psqlPatient.getNumberPhone();
        this.birthDate = psqlPatient.getBirthDate();
        this.sex = psqlPatient.isSex();
        this.bodyHeight = psqlPatient.getBodyHeight();
        this.changedLifestyleNote = psqlPatient.getChangedLifestyleNote();
        this.currentLifestyleNote = psqlPatient.getCurrentLifestyleNote();
        this.dietaryPurpose = psqlPatient.getDietaryPurpose();

        Set<AllergensType> allergensTypes = new HashSet<>();
        if (psqlPatient.isGlutenAllergy()) allergensTypes.add(AllergensType.GLUTEN);
        if (psqlPatient.isLactoseAllergy()) allergensTypes.add(AllergensType.LACTOSE);
        if (psqlPatient.isStarchAllergy()) allergensTypes.add(AllergensType.STARCH);
        this.allergens = allergensTypes;
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
