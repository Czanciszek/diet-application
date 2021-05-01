package com.springboot.dietapplication.repository.psql;

import com.springboot.dietapplication.model.psql.product.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PSQLSubcategoryRepository extends JpaRepository<Subcategory, Long> {

    Subcategory getSubcategoryByName(String name);
}
