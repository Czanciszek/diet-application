package com.springboot.dietapplication.repository.psql;

import com.springboot.dietapplication.model.psql.menu.PsqlFoodType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PsqlFoodTypeRepository extends JpaRepository<PsqlFoodType, Long> {

    PsqlFoodType getPsqlFoodTypeByName(String name);

}
