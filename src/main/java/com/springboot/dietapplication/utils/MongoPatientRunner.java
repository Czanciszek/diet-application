package com.springboot.dietapplication.utils;

import com.springboot.dietapplication.model.mongo.MongoPatient;
import com.springboot.dietapplication.model.type.PatientType;
import com.springboot.dietapplication.repository.mongo.MongoPatientRepository;
import com.springboot.dietapplication.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MongoPatientRunner {

    @Autowired
    PatientService patientService;

    @Autowired
    MongoPatientRepository mongoPatientRepository;

    public void reloadPatientsPSQLtoMongo() {

        mongoPatientRepository.deleteAll();

        List<PatientType> patientTypes = patientService.getAll();

        List<MongoPatient> mongoPatients = patientTypes
                .stream()
                .map(MongoPatient::new)
                .collect(Collectors.toList());

        DateFormat dateFormat = DateFormatter.getInstance().getIso8601Formatter();
        String currentDate = dateFormat.format(new Date());
        mongoPatients.forEach(patient -> {
            patient.setCreationDate(currentDate);
            patient.setUpdateDate(currentDate);
        });

        mongoPatientRepository.saveAll(mongoPatients);
    }
}
