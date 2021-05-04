package com.springboot.dietapplication.service.mongo;

import com.springboot.dietapplication.model.mongo.product.MongoCategory;
import com.springboot.dietapplication.model.type.ProductType;
import com.springboot.dietapplication.repository.mongo.MongoCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MongoCategoryService {

    private final MongoCategoryRepository categoryRepository;

    MongoCategoryService(MongoCategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<MongoCategory> getAll() {
        return this.categoryRepository.findAll();
    }

    public MongoCategory findCategory(ProductType productType) {
        String category = productType.getCategory();
        String subcategory = productType.getSubcategory();

        return this.categoryRepository.getCategoryByCategoryAndSubcategory(category, subcategory);
    }
}
