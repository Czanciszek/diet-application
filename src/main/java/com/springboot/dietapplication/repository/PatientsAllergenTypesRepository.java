package com.springboot.dietapplication.repository;

import com.springboot.dietapplication.model.psql.patient.PsqlPatientsAllergenTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import jakarta.transaction.Transactional;
import java.util.Set;

public interface PatientsAllergenTypesRepository extends JpaRepository<PsqlPatientsAllergenTypes, Long> {

    Set<PsqlPatientsAllergenTypes> findPsqlPatientsAllergenTypesByPatientId(long patientId);

    @Modifying
    @Transactional
    void deleteAllByPatientId(long patientId);
}
