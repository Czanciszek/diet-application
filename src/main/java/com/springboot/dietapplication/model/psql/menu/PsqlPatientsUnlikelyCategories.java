package com.springboot.dietapplication.model.psql.menu;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "patients_unlikely_categories")
public class PsqlPatientsUnlikelyCategories implements Serializable {

    @Serial
    private static final long serialVersionUID = 7691073841493970210L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "patient_id")
    private Long patientId;

    @Column(name = "category_id")
    private Long categoryId;

    public PsqlPatientsUnlikelyCategories() {

    }

    public PsqlPatientsUnlikelyCategories(long patientId, long categoryId) {
        this.patientId = patientId;
        this.categoryId = categoryId;
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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}