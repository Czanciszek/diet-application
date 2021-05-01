package com.springboot.dietapplication.repository.psql;

import com.springboot.dietapplication.model.psql.product.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PSQLCategoryRepository extends JpaRepository<Category, Long> {

    Category getCategoryByNameLike(String name);
}
