package com.springboot.dietapplication.repository;

import com.springboot.dietapplication.model.psql.patient.PsqlMeasurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PsqlMeasurementRepository extends JpaRepository<PsqlMeasurement, Long> {

    List<PsqlMeasurement> getPsqlMeasurementsByPatientId(Long patientId);
}
