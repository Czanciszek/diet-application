package com.springboot.dietapplication.controller;

import com.springboot.dietapplication.model.patient.Measurement;
import com.springboot.dietapplication.repository.MeasurementRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/measurements")
public class MeasurementController {
    private MeasurementRepository measurementRepository;

    public MeasurementController(MeasurementRepository measurementRepository) {
        this.measurementRepository = measurementRepository;
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
        return this.measurementRepository.findByPatientDocRefId(patientId);
    }
}
