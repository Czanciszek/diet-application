package com.springboot.dietapplication.service.psql;

import com.springboot.dietapplication.model.psql.product.PsqlCategory;
import com.springboot.dietapplication.model.type.ProductType;
import com.springboot.dietapplication.repository.psql.PsqlCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PsqlCategoryService {

    @Autowired
    PsqlCategoryRepository categoryRepository;

    public List<PsqlCategory> getAll() {
        return this.categoryRepository.findAll();
    }

    public Set<Long> findCategoryIdsByCategoryName(String categoryName) {
        Set<Long> categoryIdList = new HashSet<>();
        List<PsqlCategory> categories = this.categoryRepository.findMongoCategoriesByCategory(categoryName);
        for (PsqlCategory category : categories) {
            categoryIdList.add(category.getId());
        }
        return categoryIdList;
    }

    public PsqlCategory findCategoryBySubcategoryName(String subcategory) {
        return this.categoryRepository.findMongoCategoryBySubcategory(subcategory);
    }

    public PsqlCategory findCategory(ProductType productType) {
        String category = productType.getCategory();
        String subcategory = productType.getSubcategory();

        return this.categoryRepository.getCategoryByCategoryAndSubcategory(category, subcategory);
    }
}
