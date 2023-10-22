package com.springboot.dietapplication.model.type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springboot.dietapplication.model.mongo.MongoPatient;
import com.springboot.dietapplication.model.psql.patient.PsqlPatient;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class PatientType implements Serializable {

    @Serial
    private static final long serialVersionUID = -8415223607958394612L;

    private String id;

    @JsonIgnore
    private String userId;

    private String name;
    private String surname;
    private String birthDate;
    private String numberPhone;
    private String email;
    private Integer bodyHeight;

    private SexType sex;

    //Private notes about patient
    private String currentLifestyleNote;
    private String changedLifestyleNote;
    private String dietaryPurpose;

    private Set<AllergenType> allergens;
    private Set<String> unlikelyCategories;

    private List<MeasurementType> measurements;

    public PatientType() {}

    public PatientType(PsqlPatient psqlPatient) {
        this.id = String.valueOf(psqlPatient.getId());
        this.userId = String.valueOf(psqlPatient.getUserId());
        this.name = psqlPatient.getName();
        this.surname = psqlPatient.getSurname();
        this.email = psqlPatient.getEmail();
        this.numberPhone = psqlPatient.getNumberPhone();
        this.birthDate = psqlPatient.getBirthDate();
        this.sex = psqlPatient.isSex() ? SexType.FEMALE : SexType.MALE;
        this.bodyHeight = psqlPatient.getBodyHeight();
        this.changedLifestyleNote = psqlPatient.getChangedLifestyleNote();
        this.currentLifestyleNote = psqlPatient.getCurrentLifestyleNote();
        this.dietaryPurpose = psqlPatient.getDietaryPurpose();
    }

    public PatientType(MongoPatient mongoPatient) {
        this.id = mongoPatient.getId();
        this.userId = mongoPatient.getUserId();
        this.name = mongoPatient.getName();
        this.surname = mongoPatient.getSurname();
        this.email = mongoPatient.getEmail();
        this.numberPhone = mongoPatient.getPhoneNumber();
        this.birthDate = mongoPatient.getBirthDate();
        this.sex = mongoPatient.getSex();
        this.bodyHeight = mongoPatient.getBodyHeight();
        this.changedLifestyleNote = mongoPatient.getChangedLifestyleNote();
        this.currentLifestyleNote = mongoPatient.getCurrentLifestyleNote();
        this.dietaryPurpose = mongoPatient.getDietaryPurpose();
        this.allergens = mongoPatient.getAllergens();
        this.unlikelyCategories = mongoPatient.getUnlikelyCategories();
        this.measurements = mongoPatient.getMeasurements();
    }

}
