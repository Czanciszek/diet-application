package com.springboot.dietapplication.service;

import com.springboot.dietapplication.model.psql.patient.PsqlMeasurement;
import com.springboot.dietapplication.model.type.MeasurementType;
import com.springboot.dietapplication.repository.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MeasurementService {

    @Autowired
    MeasurementRepository measurementRepository;

    public List<MeasurementType> getAll() {
        List<MeasurementType> measurementTypeList = new ArrayList<>();

        List<PsqlMeasurement> measurements = this.measurementRepository.findAll();
        for (PsqlMeasurement measurement : measurements) {
            measurementTypeList.add(new MeasurementType(measurement));
        }

        return measurementTypeList;
    }

    public MeasurementType getMeasurementById(Long measurementId) {
        Optional<PsqlMeasurement> measurement = this.measurementRepository.findById(measurementId);
        return measurement.map(MeasurementType::new).orElseGet(MeasurementType::new);
    }

    public List<MeasurementType> getMeasurementsByPatientId(Long patientId) {
        List<MeasurementType> measurementTypeList = new ArrayList<>();

        List<PsqlMeasurement> measurements = this.measurementRepository.getPsqlMeasurementsByPatientId(patientId);
        for (PsqlMeasurement measurement : measurements) {
            measurementTypeList.add(new MeasurementType(measurement));
        }

        return measurementTypeList;
    }

    public ResponseEntity<MeasurementType> insert(MeasurementType measurement) {
        PsqlMeasurement psqlMeasurement = new PsqlMeasurement(measurement);

        this.measurementRepository.save(psqlMeasurement);
        measurement.setId(psqlMeasurement.getId());

        return ResponseEntity.ok().body(measurement);
    }


}
