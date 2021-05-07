package com.springboot.dietapplication.controller;

import com.springboot.dietapplication.model.patient.Measurement;
import com.springboot.dietapplication.model.patient.MongoPatient;
import com.springboot.dietapplication.repository.mongo.MeasurementRepository;
import com.springboot.dietapplication.repository.mongo.MongoPatientRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/measurements")
public class MeasurementController {
    private final MeasurementRepository measurementRepository;
    private final MongoPatientRepository patientRepository;

    public MeasurementController(MeasurementRepository measurementRepository, MongoPatientRepository patientRepository) {
        this.measurementRepository = measurementRepository;
        this.patientRepository = patientRepository;
    }

    @GetMapping
    public List<Measurement> getAll() {
        return this.measurementRepository.findAll();
    }

    @GetMapping(path = "/{measurementId}")
    public Optional<Measurement> getMeasurementsById(@PathVariable("measurementId") String measurementId) {
        return this.measurementRepository.findById(measurementId);
    }

    @GetMapping(path = "/patient/{patientId}")
    public List<Measurement> getMeasurementsByPatientId(@PathVariable("patientId") String patientId) {
        return this.measurementRepository.findByPatientId(patientId);
    }

    @PostMapping(path = "/{patientId}", produces = "application/json")
    ResponseEntity<Measurement> insertMeasurement(@PathVariable("patientId") String patientId,
                                                  @RequestBody Measurement measurement) throws NoSuchFieldException {
        Optional<MongoPatient> patient = patientRepository.findById(patientId);
        if (patient.isPresent()) {
            measurement.setPatientId(patientId);
            measurementRepository.save(measurement);
            return ResponseEntity.ok().body(measurement);
        }
        return ResponseEntity.notFound().build();
    }
}
