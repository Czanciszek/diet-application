package com.springboot.dietapplication.repository;

import com.springboot.dietapplication.model.psql.menu.PsqlAllergenType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllergenTypeRepository extends JpaRepository<PsqlAllergenType, Long> {

    PsqlAllergenType getPsqlAllergenTypeByName(String name);
}
