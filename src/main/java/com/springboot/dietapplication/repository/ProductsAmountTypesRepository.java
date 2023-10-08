package com.springboot.dietapplication.repository;

import com.springboot.dietapplication.model.psql.product.PsqlProductsAmountTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import java.util.Set;

@Repository
public interface ProductsAmountTypesRepository extends JpaRepository<PsqlProductsAmountTypes, Long> {

    Set<PsqlProductsAmountTypes> findPsqlProductsAmountTypesByProductId(long id);

    @Modifying
    @Transactional
    void deleteAllByProductId(long productId);
}
