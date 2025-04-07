package com.springboot.dietapplication.utils;

import com.springboot.dietapplication.model.type.ClinicType;
import com.springboot.dietapplication.repository.mongo.MongoUserEntityRepository;
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
    MongoUserEntityRepository userEntityRepository;

    public void addCustomClinic() {

        var clinic = new ClinicType();
        clinic.setName("Cure Diet");
        clinic.setAddress("None");
        clinic.setMaintainerUserId("8");

        clinicService.insert(clinic);
    }

    public void setClinicForUsers() {

        var clinicId = clinicService
                .getAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No clinics"))
                .getId();

        var users = userEntityRepository.findAll();

        var updatedUsers = users
                .stream()
                .peek(p -> p.setClinicId(clinicId))
                .collect(Collectors.toList());

        userEntityRepository.saveAll(updatedUsers);
    }
}
