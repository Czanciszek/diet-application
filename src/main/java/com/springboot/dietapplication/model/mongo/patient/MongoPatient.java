package com.springboot.dietapplication.model.mongo.patient;

import com.springboot.dietapplication.model.type.*;
import com.springboot.dietapplication.model.type.enums.AllergenType;
import com.springboot.dietapplication.model.type.enums.PatientRoleType;
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

    private String clinicId;
    private String employeeId;
    private PatientRoleType roleType;

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

    public MongoPatient(Patient patient) {
        this.id = patient.getId();
        this.clinicId = patient.getClinicId();
        this.employeeId = patient.getEmployeeId();
        this.roleType = patient.getRoleType();

        this.name = patient.getName();
        this.surname = patient.getSurname();
        this.birthDate = patient.getBirthDate();
        this.phoneNumber = patient.getNumberPhone();
        this.email = patient.getEmail();
        this.sex = patient.getSex();
        this.bodyHeight = patient.getBodyHeight();

        this.currentLifestyleNote = patient.getCurrentLifestyleNote();
        this.changedLifestyleNote = patient.getChangedLifestyleNote();
        this.dietaryPurpose = patient.getDietaryPurpose();

        this.allergens = patient.getAllergens();
        this.unlikelyCategories = patient.getUnlikelyCategories();
        this.measurements = patient.getMeasurements();
        this.menus = patient.getMenus();
    }


}
