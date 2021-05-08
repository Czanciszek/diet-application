package com.springboot.dietapplication.service.mongo;

import com.springboot.dietapplication.model.mongo.patient.MongoMeasurement;
import com.springboot.dietapplication.model.type.MeasurementType;
import com.springboot.dietapplication.repository.mongo.MongoMeasurementRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MongoMeasurementService {

    private final MongoMeasurementRepository measurementRepository;

    public MongoMeasurementService(MongoMeasurementRepository measurementRepository) {
        this.measurementRepository = measurementRepository;
    }

    public List<MeasurementType> getAll() {
        List<MeasurementType> measurementTypeList = new ArrayList<>();

        List<MongoMeasurement> measurements = this.measurementRepository.findAll();
        for (MongoMeasurement measurement : measurements) {
            measurementTypeList.add(new MeasurementType(measurement));
        }

        return measurementTypeList;
    }

    public MeasurementType getMeasurementById(String measurementId) {
        Optional<MongoMeasurement> measurement = this.measurementRepository.findById(measurementId);
        return measurement.map(MeasurementType::new).orElseGet(MeasurementType::new);
    }

    public List<MeasurementType> getMeasurementsByPatientId(String patientId) {
        List<MeasurementType> measurementTypeList = new ArrayList<>();

        List<MongoMeasurement> measurements = this.measurementRepository.findByPatientId(patientId);
        for (MongoMeasurement measurement : measurements) {
            measurementTypeList.add(new MeasurementType(measurement));
        }

        return measurementTypeList;
    }

    public ResponseEntity<MeasurementType> insert(MeasurementType measurement) {
        MongoMeasurement mongoMeasurement = new MongoMeasurement(measurement);

        this.measurementRepository.save(mongoMeasurement);
        measurement.setId(mongoMeasurement.getId());

        return ResponseEntity.ok().body(measurement);
    }


}
