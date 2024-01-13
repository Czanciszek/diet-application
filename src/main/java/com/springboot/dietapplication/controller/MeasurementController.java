package com.springboot.dietapplication.controller;

import com.springboot.dietapplication.model.type.MeasurementType;
import com.springboot.dietapplication.service.MeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Deprecated(since = "0.1.0", forRemoval = true)
@RestController
@RequestMapping("api/v1/measurements")
public class MeasurementController {

    @Autowired
    MeasurementService measurementService;

    @GetMapping
    public List<MeasurementType> getAll() {
        return this.measurementService.getAll();
    }

    @GetMapping(path = "/{measurementId}")
    public MeasurementType getMeasurementById(@PathVariable("measurementId") Long measurementId) {
        return this.measurementService.getMeasurementById(measurementId);
    }

    @GetMapping(path = "/patient/{patientId}")
    public List<MeasurementType> getMeasurementsByPatientId(@PathVariable("patientId") Long patientId) {
        return this.measurementService.getMeasurementsByPatientId(patientId);
    }

    @PostMapping(produces = "application/json")
    ResponseEntity<MeasurementType> insert(@RequestBody MeasurementType measurement) {
        return this.measurementService.insert(measurement);
    }
}
