package com.springboot.dietapplication.service.mongo;

import com.springboot.dietapplication.model.excel.ProductExcel;
import com.springboot.dietapplication.model.mongo.product.MongoCategory;
import com.springboot.dietapplication.model.mongo.product.MongoProduct;
import com.springboot.dietapplication.model.type.FoodPropertiesType;
import com.springboot.dietapplication.repository.mongo.MongoCategoryRepository;
import com.springboot.dietapplication.repository.mongo.MongoProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MongoDataService {

    private final MongoCategoryRepository categoryRepository;
    private final MongoProductRepository productRepository;

    public MongoDataService(MongoCategoryRepository categoryRepository, MongoProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    public void saveProducts(List<ProductExcel> importedProducts) {
        List<MongoProduct> products = new ArrayList<>();
        for (ProductExcel productExcel : importedProducts) {
            MongoProduct product = new MongoProduct(productExcel);

            FoodPropertiesType foodProperties = new FoodPropertiesType(productExcel);
            product.setFoodProperties(foodProperties);

            MongoCategory category = new MongoCategory(
                    productExcel.getCategory(),
                    productExcel.getSubcategory()
            );

            MongoCategory actualCategory =
                    this.categoryRepository.getCategoryByCategoryAndSubcategory(
                            category.getCategory(),
                            category.getSubcategory());
            if (actualCategory != null && actualCategory.getId() != null) {
                category.setId(actualCategory.getId());
            } else {
                this.categoryRepository.save(category);
            }

            product.setCategoryId(category.getId());
            products.add(product);
        }

        this.productRepository.saveAll(products);
    }

    public void clearDatabase() {
        this.categoryRepository.deleteAll();
        this.productRepository.deleteAll();
    }

    public void createBackup() {
        try {
            List<String> command = Arrays.asList(
                    "mongodump.exe",
                    "--db", "DietAppDb");

            Process process = new ProcessBuilder(command).start();
            process.waitFor();
            process.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void restoreBackup() {
        try {
            List<String> command = Arrays.asList(
                    "mongorestore.exe",
                    "--db", "DietAppDb",
                    "dump/DietAppDb/Products.bson");

            Process process = new ProcessBuilder(command).start();
            process.waitFor();
            process.destroy();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
