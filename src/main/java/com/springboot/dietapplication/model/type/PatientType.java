package com.springboot.dietapplication.model.type;

import com.springboot.dietapplication.model.psql.patient.PsqlPatient;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class PatientType implements Serializable {

    private static final long serialVersionUID = -8415223607958394612L;

    private Long id;
    private String name;
    private String surname;
    private String birthDate;
    private String numberPhone;
    private String email;
    private int bodyHeight;

    /**
     * Boolean indicates the sex of the patient in the meaning of:
     * True - Female
     * False - Male
     */
    private boolean sex;

    //Private notes about patient
    private String currentLifestyleNote;
    private String changedLifestyleNote;
    private String dietaryPurpose;

    private Set<AllergensType> allergens;
    private Set<String> unlikelyCategories;

    public PatientType() {

    }

    public PatientType(PsqlPatient psqlPatient) {
        this.id = psqlPatient.getId();
        this.name = psqlPatient.getName();
        this.surname = psqlPatient.getSurname();
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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
