package com.springboot.dietapplication.repository.psql;

import com.springboot.dietapplication.model.psql.product.PsqlCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PSQLCategoryRepository extends JpaRepository<PsqlCategory, Long> {

    PsqlCategory getCategoryByCategoryAndSubcategory(String category, String subcategory);

}
