package com.springboot.dietapplication.repository.psql;

import com.springboot.dietapplication.model.psql.menu.PsqlMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PsqlMenuRepository extends JpaRepository<PsqlMenu, Long> {

    List<PsqlMenu> findPsqlMenusByPatientId(long patientId);
}
