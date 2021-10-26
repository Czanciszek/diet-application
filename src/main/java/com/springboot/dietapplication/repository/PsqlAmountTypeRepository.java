package com.springboot.dietapplication.repository;

import com.springboot.dietapplication.model.psql.menu.PsqlAmountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PsqlAmountTypeRepository extends JpaRepository<PsqlAmountType, Long> {

    PsqlAmountType getPsqlAmountTypeByName(String name);
}
