package com.springboot.dietapplication.repository;

import com.springboot.dietapplication.model.psql.dish.PsqlProductDish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import java.util.List;

@Repository
public interface ProductDishRepository extends JpaRepository<PsqlProductDish, Long> {

    List<PsqlProductDish> findPsqlProductDishesByDishId(Long dishId);

    List<PsqlProductDish> findPsqlProductDishesByProductId(Long productId);

    @Transactional
    void deletePsqlProductDishesByDishId(Long dishId);
}
