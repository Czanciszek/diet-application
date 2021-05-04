package com.springboot.dietapplication.service.psql;

import com.springboot.dietapplication.model.psql.product.PsqlCategory;
import com.springboot.dietapplication.model.type.ProductType;
import com.springboot.dietapplication.repository.psql.PSQLCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PsqlCategoryService {

    @Autowired
    PSQLCategoryRepository categoryRepository;

    public PsqlCategory findCategory(ProductType productType) {
        String category = productType.getCategory();
        String subcategory = productType.getSubcategory();

        return this.categoryRepository.getCategoryByCategoryAndSubcategory(category, subcategory);
    }
}
