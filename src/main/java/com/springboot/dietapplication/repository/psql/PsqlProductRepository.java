package com.springboot.dietapplication.repository.psql;

import com.springboot.dietapplication.model.psql.product.PsqlProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface PsqlProductRepository extends JpaRepository<PsqlProduct, Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM products", nativeQuery = true)
    void truncate();

}
