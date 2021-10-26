package com.springboot.dietapplication.repository;

import com.springboot.dietapplication.model.psql.product.PsqlCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<PsqlCategory, Long> {

    PsqlCategory getCategoryByCategoryAndSubcategory(String category, String subcategory);

    List<PsqlCategory> findPsqlCategoriesByCategory(String category);

    PsqlCategory findPsqlCategoryBySubcategory(String subcategory);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM categories", nativeQuery = true)
    void truncate();
}
