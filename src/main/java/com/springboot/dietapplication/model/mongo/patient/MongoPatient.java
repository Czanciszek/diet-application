package com.springboot.dietapplication.model.mongo.patient;

import com.springboot.dietapplication.model.type.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Document("patients")
public class MongoPatient {

    @Id
    private String id;

    private String userId;

    private String name;
    private String surname;
    private String birthDate;
    private String phoneNumber;
    private String email;
    private SexType sex;
    private Integer bodyHeight;

    private String currentLifestyleNote;
    private String changedLifestyleNote;
    private String dietaryPurpose;

    private Set<AllergenType> allergens;
    // TODO: Switch to CategoryType
    private Set<String> unlikelyCategories;
    private List<MeasurementType> measurements;
    private List<String> menus;

    private String creationDate;
    private String updateDate;
    private String deletionDate;

    public MongoPatient(PatientType patientType) {
        this.id = patientType.getId();
        this.userId = patientType.getUserId();

        this.name = patientType.getName();
        this.surname = patientType.getSurname();
        this.birthDate = patientType.getBirthDate();
        this.birthDate = patientType.getBirthDate();
        this.phoneNumber = patientType.getNumberPhone();
        this.email = patientType.getEmail();
        this.sex = patientType.getSex();
        this.bodyHeight = patientType.getBodyHeight();

        this.currentLifestyleNote = patientType.getCurrentLifestyleNote();
        this.changedLifestyleNote = patientType.getChangedLifestyleNote();
        this.dietaryPurpose = patientType.getDietaryPurpose();

        this.allergens = patientType.getAllergens();
        this.unlikelyCategories = patientType.getUnlikelyCategories();
        this.measurements = patientType.getMeasurements();
        this.menus = patientType.getMenus();
    }


}
