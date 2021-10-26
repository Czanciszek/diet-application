package com.springboot.dietapplication.service;

import com.springboot.dietapplication.model.excel.ProductExcel;
import com.springboot.dietapplication.model.psql.product.PsqlCategory;
import com.springboot.dietapplication.model.psql.product.PsqlProduct;
import com.springboot.dietapplication.model.psql.properties.PsqlFoodProperties;
import com.springboot.dietapplication.repository.CategoryRepository;
import com.springboot.dietapplication.repository.FoodPropertiesRepository;
import com.springboot.dietapplication.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DataService {

    FoodPropertiesRepository foodPropertiesRepository;
    CategoryRepository categoryRepository;
    ProductRepository productRepository;

    public void saveProducts(List<ProductExcel> importedProducts) {
        List<PsqlProduct> products = new ArrayList<>();
        for (ProductExcel productExcel : importedProducts) {
            PsqlProduct psqlProduct = new PsqlProduct(productExcel);

            PsqlFoodProperties foodProperties = new PsqlFoodProperties(productExcel);
            foodPropertiesRepository.save(foodProperties);

            PsqlCategory category = new PsqlCategory(
                    productExcel.getCategory(),
                    productExcel.getSubcategory()
            );

            PsqlCategory actualCategory =
                    this.categoryRepository.getCategoryByCategoryAndSubcategory(
                            category.getCategory(),
                            category.getSubcategory());
            if (actualCategory != null && actualCategory.getCategory() != null) {
                category.setId(actualCategory.getId());
            } else {
                this.categoryRepository.save(category);
            }

            psqlProduct.setCategoryId(category.getId());
            psqlProduct.setFoodPropertiesId(foodProperties.getId());

            products.add(psqlProduct);
        }

        productRepository.saveAll(products);
    }

    public void clearDatabase() {
        this.productRepository.truncate();
        this.foodPropertiesRepository.truncate();
        this.categoryRepository.truncate();
    }

    public void createBackup() {
        try {
            List<String> command = Arrays.asList(
                    "\"C:/Program Files/PostgreSQL/11/bin/pg_dump\"",
                    "--dbname=postgresql://postgres:postgres@127.0.0.1:5432/DietApp",
                    "--file", "\"dump/psqlDump.bak\""
            );

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
                    "\"C:/Program Files/PostgreSQL/11/bin/psql\"",
                    "-f", "\"dump/psqlDump.bak\"",
                    "--verbose",
                    "--dbname=postgresql://postgres:postgres@127.0.0.1:5432/DietApp"
            );

            Process process = new ProcessBuilder(command).start();
            process.waitFor();
            process.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
