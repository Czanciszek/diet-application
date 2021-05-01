package com.springboot.dietapplication.repository.psql;

import com.springboot.dietapplication.model.psql.properties.FoodProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PSQLFoodPropertiesRepository extends JpaRepository<FoodProperties, Long> {

}
