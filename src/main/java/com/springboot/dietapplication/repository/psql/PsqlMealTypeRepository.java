package com.springboot.dietapplication.repository.psql;

import com.springboot.dietapplication.model.psql.menu.PsqlMealType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PsqlMealTypeRepository extends JpaRepository<PsqlMealType, Long> {

    PsqlMealType getPsqlMealTypeByName(String name);

}
