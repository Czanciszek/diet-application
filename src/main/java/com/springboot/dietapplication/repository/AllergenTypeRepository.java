package com.springboot.dietapplication.repository;

import com.springboot.dietapplication.model.psql.menu.PsqlAllergenType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Deprecated(since = "0.1.0", forRemoval = true)
@Repository
public interface AllergenTypeRepository extends JpaRepository<PsqlAllergenType, Long> {

    PsqlAllergenType getPsqlAllergenTypeByName(String name);
}
