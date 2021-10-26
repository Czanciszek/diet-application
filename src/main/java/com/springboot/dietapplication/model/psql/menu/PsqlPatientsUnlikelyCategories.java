package com.springboot.dietapplication.model.psql.menu;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "patients_unlikely_categories")
public class PsqlPatientsUnlikelyCategories implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "patient_id")
    private long patientId;

    @Column(name = "category_id")
    private long categoryId;

    public PsqlPatientsUnlikelyCategories() {

    }

    public PsqlPatientsUnlikelyCategories(long patientId, long categoryId) {
        this.patientId = patientId;
        this.categoryId = categoryId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }
}