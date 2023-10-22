package com.springboot.dietapplication.model.psql.patient;

import com.springboot.dietapplication.model.type.PatientType;

import com.springboot.dietapplication.model.type.SexType;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "patients")
public class PsqlPatient implements Serializable {

    @Serial
    private static final long serialVersionUID = 6549760342516106979L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "birthdate")
    private String birthDate;

    @Column(name = "phone_number")
    private String numberPhone;

    @Column(name = "email")
    private String email;

    @Column(name = "sex")
    private boolean sex;

    @Column(name = "body_height")
    private int bodyHeight;

    @Column(name = "current_lifestyle_note")
    private String currentLifestyleNote;

    @Column(name = "changed_lifestyle_note")
    private String changedLifestyleNote;

    @Column(name = "dietary_purpose")
    private String dietaryPurpose;

    public PsqlPatient() {

    }

    public PsqlPatient(PatientType patientType) {
        this.id = Long.valueOf(patientType.getId());
        this.name = patientType.getName();
        this.surname = patientType.getSurname();
        this.email = patientType.getEmail();
        this.numberPhone = patientType.getNumberPhone();
        this.birthDate = patientType.getBirthDate();
        this.sex = patientType.getSex().equals(SexType.FEMALE);
        this.bodyHeight = patientType.getBodyHeight();
        this.changedLifestyleNote = patientType.getChangedLifestyleNote();
        this.currentLifestyleNote = patientType.getCurrentLifestyleNote();
        this.dietaryPurpose = patientType.getDietaryPurpose();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

}