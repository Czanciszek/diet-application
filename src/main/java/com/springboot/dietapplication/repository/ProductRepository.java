package com.springboot.dietapplication.repository;

import com.springboot.dietapplication.model.psql.product.PsqlProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
public interface ProductRepository extends JpaRepository<PsqlProduct, Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM products", nativeQuery = true)
    void truncate();

}
