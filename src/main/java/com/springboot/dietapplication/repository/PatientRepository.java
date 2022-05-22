package com.springboot.dietapplication.repository;

import com.springboot.dietapplication.model.psql.patient.PsqlPatient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<PsqlPatient, Long> {

    List<PsqlPatient> findPsqlPatientsByUserId(Long userId);

}
