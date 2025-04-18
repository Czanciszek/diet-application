package com.springboot.dietapplication.utils;

import com.springboot.dietapplication.model.mongo.patient.MongoEmployee;
import com.springboot.dietapplication.model.mongo.user.MongoUserEntity;
import com.springboot.dietapplication.model.type.EmployeeRoleType;
import com.springboot.dietapplication.repository.mongo.MongoEmployeeRepository;
import com.springboot.dietapplication.repository.mongo.MongoUserEntityRepository;
import com.springboot.dietapplication.service.ClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class MongoEmployeeRunner {

    @Autowired
    ClinicService clinicService;

    @Autowired
    MongoEmployeeRepository employeeRepository;

    @Autowired
    MongoUserEntityRepository userEntityRepository;

    public void createEmployees() {

        var clinicId = clinicService
                .getAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No clinics"))
                .getId();

        var users = userEntityRepository.findAll();

        for (MongoUserEntity user : users) {

            var employee = new MongoEmployee();
            employee.setClinicId(clinicId);
            employee.setRoleType(EmployeeRoleType.OWNER);
            employee.setEmail(user.getEmail());
            employee.setName("");
            employee.setSurname("");
            employee.setPdfFooter(user.getPdfFooter());

            employeeRepository.save(employee);
        }
    }
}
