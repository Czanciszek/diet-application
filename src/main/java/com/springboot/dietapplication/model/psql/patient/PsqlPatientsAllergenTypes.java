package com.springboot.dietapplication.model.psql.patient;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Deprecated(since = "0.1.0", forRemoval = true)
@Entity
@Table(name = "patients_allergens")
public class PsqlPatientsAllergenTypes implements Serializable {

    @Serial
    private static final long serialVersionUID = 4356539590928249464L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "patient_id")
    private Long patientId;

    @Column(name = "allergen_id")
    private Long allergenTypeId;

    public PsqlPatientsAllergenTypes() {
    }

    public PsqlPatientsAllergenTypes(Long patientId, Long allergenTypeId) {
        this.patientId = patientId;
        this.allergenTypeId = allergenTypeId;
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

    public Long getAllergenTypeId() {
        return allergenTypeId;
    }

    public void setAllergenTypeId(Long allergenTypeId) {
        this.allergenTypeId = allergenTypeId;
    }
}
