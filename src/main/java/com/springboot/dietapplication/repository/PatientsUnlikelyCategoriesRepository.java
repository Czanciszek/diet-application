package com.springboot.dietapplication.repository;

import com.springboot.dietapplication.model.psql.menu.PsqlPatientsUnlikelyCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import java.util.Set;

@Repository
public interface PatientsUnlikelyCategoriesRepository extends JpaRepository<PsqlPatientsUnlikelyCategories, Long> {

    Set<PsqlPatientsUnlikelyCategories> findPsqlPatientsUnlikelyCategoriesByPatientId(long id);

    @Modifying
    @Transactional
    void deleteAllByPatientId(long patientId);
}
