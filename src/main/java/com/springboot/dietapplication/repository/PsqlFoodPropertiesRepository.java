package com.springboot.dietapplication.repository;

import com.springboot.dietapplication.model.psql.properties.PsqlFoodProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface PsqlFoodPropertiesRepository extends JpaRepository<PsqlFoodProperties, Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM food_properties", nativeQuery = true)
    void truncate();
}
