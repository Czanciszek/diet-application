package com.springboot.dietapplication.controller.mongo;

import com.springboot.dietapplication.model.type.MeasurementType;
import com.springboot.dietapplication.service.mongo.MongoMeasurementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mongo/measurements")
public class MongoMeasurementController {

    private final MongoMeasurementService measurementService;

    public MongoMeasurementController(MongoMeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    @GetMapping
    public List<MeasurementType> getAll() {
        return this.measurementService.getAll();
    }

    @GetMapping(path = "/{measurementId}")
    public MeasurementType getMeasurementById(@PathVariable("measurementId") String measurementId) {
        return this.measurementService.getMeasurementById(measurementId);
    }

    @GetMapping(path = "/patient/{patientId}")
    public List<MeasurementType> getMeasurementsByPatientId(@PathVariable("patientId") String patientId) {
        return this.measurementService.getMeasurementsByPatientId(patientId);
    }

    @PostMapping(path = "/{patientId}", produces = "application/json")
    ResponseEntity<MeasurementType> insert(@RequestBody MeasurementType measurement) {
        return this.measurementService.insert(measurement);
    }
}
