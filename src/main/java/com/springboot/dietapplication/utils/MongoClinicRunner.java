package com.springboot.dietapplication.utils;

import com.springboot.dietapplication.model.type.Clinic;
import com.springboot.dietapplication.model.type.enums.PatientRoleType;
import com.springboot.dietapplication.repository.MongoPatientRepository;
import com.springboot.dietapplication.service.ClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;

@Service
public class MongoClinicRunner {

    @Autowired
    ClinicService clinicService;

    @Autowired
    MongoPatientRepository patientRepository;

    public void addCustomClinic() {

        var clinic = new Clinic();
        clinic.setName("Cure Diet");
        clinic.setAddress("");

        clinicService.insert(clinic);
    }

    public void updatePatients() {

        var clinicId = clinicService
                .getAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No clinics"))
                .getId();

        var patients = patientRepository.findAll();

        var updatedPatients = patients
                .stream()
                .peek(p -> p.setClinicId(clinicId))
                .peek(p -> p.setRoleType(PatientRoleType.PERSONAL))
                .collect(Collectors.toList());

        patientRepository.saveAll(updatedPatients);
    }
}
