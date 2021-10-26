package com.springboot.dietapplication.repository;

import com.springboot.dietapplication.model.psql.dish.PsqlProductDish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface PsqlProductDishRepository extends JpaRepository<PsqlProductDish, Long> {

    List<PsqlProductDish> findPsqlProductDishesByDishId(Long dishId);

    @Transactional
    void deletePsqlProductDishesByDishId(Long dishId);
}
