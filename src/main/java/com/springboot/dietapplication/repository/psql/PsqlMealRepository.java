package com.springboot.dietapplication.repository.psql;

import com.springboot.dietapplication.model.psql.menu.PsqlFoodType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PsqlMealRepository extends JpaRepository<PsqlFoodType, Long> {


}
