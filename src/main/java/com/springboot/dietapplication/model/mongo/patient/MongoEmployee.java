package com.springboot.dietapplication.model.mongo.patient;

import com.springboot.dietapplication.model.type.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document("employees")
public class MongoEmployee {

    @Id
    private String id;

    private String clinicId;
    private EmployeeRoleType roleType;

    private String email;
    private String name;
    private String surname;
    private String pdfFooter;

    public MongoEmployee(EmployeeType employeeType) {
        this.id = employeeType.getId();
        this.clinicId = employeeType.getClinicId();
        this.roleType = employeeType.getRoleType();
        this.email = employeeType.getEmail();
        this.name = employeeType.getName();
        this.surname = employeeType.getSurname();
        this.pdfFooter = employeeType.getPdfFooter();
    }
}
