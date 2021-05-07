package com.springboot.dietapplication.service.mongo;

import com.springboot.dietapplication.model.mongo.product.MongoCategory;
import com.springboot.dietapplication.model.type.ProductType;
import com.springboot.dietapplication.repository.mongo.MongoCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MongoCategoryService {

    private final MongoCategoryRepository categoryRepository;

    MongoCategoryService(MongoCategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<MongoCategory> getAll() {
        return this.categoryRepository.findAll();
    }

    public Set<String> findCategoryIdsByCategoryName(String categoryName) {
        Set<String> categoryIdList = new HashSet<>();
        List<MongoCategory> categories = this.categoryRepository.findMongoCategoriesByCategory(categoryName);
        for (MongoCategory category : categories) {
            categoryIdList.add(category.getId());
        }
        return categoryIdList;
    }

    public MongoCategory findCategoryBySubcategoryName(String subcategory) {
        return this.categoryRepository.findMongoCategoryBySubcategory(subcategory);
    }

    public MongoCategory findCategory(ProductType productType) {
        String category = productType.getCategory();
        String subcategory = productType.getSubcategory();

        return this.categoryRepository.getCategoryByCategoryAndSubcategory(category, subcategory);
    }
}
