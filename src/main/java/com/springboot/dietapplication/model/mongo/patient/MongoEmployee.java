package com.springboot.dietapplication.model.mongo.patient;

import com.springboot.dietapplication.model.type.*;
import com.springboot.dietapplication.model.type.enums.EmployeeRoleType;
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

    public MongoEmployee(Employee employee) {
        this.id = employee.getId();
        this.clinicId = employee.getClinicId();
        this.roleType = employee.getRoleType();
        this.email = employee.getEmail();
        this.name = employee.getName();
        this.surname = employee.getSurname();
        this.pdfFooter = employee.getPdfFooter();
    }
}
