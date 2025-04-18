package com.springboot.dietapplication.model.type;

import com.springboot.dietapplication.model.mongo.patient.MongoEmployee;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeType implements Serializable {

    @Serial
    private static final long serialVersionUID = -4858370743727517L;

    private String id;

    private String clinicId;
    private EmployeeRoleType roleType;

    private String email;
    private String name;
    private String surname;
    private String pdfFooter;

    public EmployeeType(MongoEmployee mongoEmployee) {
        this.id = mongoEmployee.getId();
        this.clinicId = mongoEmployee.getClinicId();
        this.email = mongoEmployee.getEmail();
        this.name = mongoEmployee.getName();
        this.surname = mongoEmployee.getSurname();
        this.pdfFooter = mongoEmployee.getPdfFooter();
    }

}
