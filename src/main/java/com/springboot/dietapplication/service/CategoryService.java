package com.springboot.dietapplication.service;

import com.springboot.dietapplication.model.psql.product.PsqlCategory;
import com.springboot.dietapplication.model.type.ProductType;
import com.springboot.dietapplication.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Deprecated(since = "0.1.0", forRemoval = true)
@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public List<PsqlCategory> getAll() {
        return this.categoryRepository.findAll();
    }

    public Set<Long> findCategoryIdsByCategoryName(String categoryName) {
        Set<Long> categoryIdList = new HashSet<>();
        List<PsqlCategory> categories = this.categoryRepository.findPsqlCategoriesByCategory(categoryName);
        for (PsqlCategory category : categories) {
            categoryIdList.add(category.getId());
        }
        return categoryIdList;
    }

    public PsqlCategory findCategoryBySubcategoryName(String subcategory) {
        return this.categoryRepository.findPsqlCategoryBySubcategory(subcategory);
    }

    public PsqlCategory findCategory(ProductType productType) {
        String category = productType.getCategory().getCategory();
        String subcategory = productType.getCategory().getSubcategory();

        return this.categoryRepository.getCategoryByCategoryAndSubcategory(category, subcategory);
    }
}
