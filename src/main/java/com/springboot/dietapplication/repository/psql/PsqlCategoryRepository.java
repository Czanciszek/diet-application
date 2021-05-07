package com.springboot.dietapplication.repository.psql;

import com.springboot.dietapplication.model.psql.product.PsqlCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PsqlCategoryRepository extends JpaRepository<PsqlCategory, Long> {

    PsqlCategory getCategoryByCategoryAndSubcategory(String category, String subcategory);

    List<PsqlCategory> findMongoCategoriesByCategory(String category);

    PsqlCategory findMongoCategoryBySubcategory(String subcategory);
}
