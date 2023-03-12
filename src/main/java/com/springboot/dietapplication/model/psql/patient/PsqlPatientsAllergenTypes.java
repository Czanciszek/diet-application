package com.springboot.dietapplication.model.psql.patient;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "patients_allergens")
public class PsqlPatientsAllergenTypes implements Serializable {

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
