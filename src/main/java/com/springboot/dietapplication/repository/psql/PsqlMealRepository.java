package com.springboot.dietapplication.repository.psql;

import com.springboot.dietapplication.model.psql.menu.PsqlMealType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PsqlMealRepository extends JpaRepository<PsqlMealType, Long> {


}
