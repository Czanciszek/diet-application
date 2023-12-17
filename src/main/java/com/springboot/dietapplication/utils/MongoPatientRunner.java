package com.springboot.dietapplication.utils;

import com.springboot.dietapplication.model.mongo.patient.MongoPatient;
import com.springboot.dietapplication.model.type.PatientType;
import com.springboot.dietapplication.repository.mongo.MongoPatientRepository;
import com.springboot.dietapplication.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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
                .map( p -> {
                    MongoPatient mongoPatient = new MongoPatient(p);

                    Instant instantBirthDate = Instant.parse(p.getBirthDate());
                    LocalDate birthLocalDate = Instant
                            .ofEpochMilli(instantBirthDate.toEpochMilli())
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                    mongoPatient.setBirthDate(birthLocalDate.toString());

                    mongoPatient.getMeasurements().forEach(m-> {
                        Instant instantMeasurementDate = Instant.parse(m.getMeasurementDate());
                        LocalDate measurementLocalDate = Instant
                                .ofEpochMilli(instantMeasurementDate.toEpochMilli())
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate();
                        m.setMeasurementDate(measurementLocalDate.toString());
                    });

                    return mongoPatient;
                })
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
