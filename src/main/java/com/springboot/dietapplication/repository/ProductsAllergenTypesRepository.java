package com.springboot.dietapplication.repository;

import com.springboot.dietapplication.model.psql.product.PsqlProductsAllergenTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import java.util.Set;

@Repository
public interface ProductsAllergenTypesRepository extends JpaRepository<PsqlProductsAllergenTypes, Long> {

    Set<PsqlProductsAllergenTypes> findPsqlProductsAllergenTypesByProductId(long productId);

    @Modifying
    @Transactional
    void deleteAllByProductId(long productId);
}
