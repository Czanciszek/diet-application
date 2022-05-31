package com.springboot.dietapplication.repository;

import com.springboot.dietapplication.model.psql.patient.PsqlPatient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<PsqlPatient, Long> {

}
