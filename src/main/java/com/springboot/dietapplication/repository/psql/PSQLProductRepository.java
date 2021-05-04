package com.springboot.dietapplication.repository.psql;

import com.springboot.dietapplication.model.psql.product.PsqlProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface PSQLProductRepository extends JpaRepository<PsqlProduct, Long> {

    List<PsqlProduct> findProductsByIdIn(Set<Long> productIdList);

}
