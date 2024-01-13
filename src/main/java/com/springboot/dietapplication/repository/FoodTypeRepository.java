package com.springboot.dietapplication.repository;

import com.springboot.dietapplication.model.psql.menu.PsqlFoodType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Deprecated(since = "0.1.0", forRemoval = true)
@Repository
public interface FoodTypeRepository extends JpaRepository<PsqlFoodType, Long> {

    PsqlFoodType getPsqlFoodTypeByName(String name);

}
