package com.springboot.dietapplication;

import com.springboot.dietapplication.model.product.Category;
import com.springboot.dietapplication.model.product.Product;
import com.springboot.dietapplication.model.excel.ProductExcel;
import com.springboot.dietapplication.model.mongo.user.User;
import com.springboot.dietapplication.model.psql.product.Subcategory;
import com.springboot.dietapplication.model.psql.properties.FoodProperties;
import com.springboot.dietapplication.repository.mongo.*;
import com.springboot.dietapplication.repository.psql.*;
import io.github.biezhi.excel.plus.Reader;
import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;

@Component
public class DbSeeder implements CommandLineRunner {

    private final MongoProductRepository mongoProductRepository;
    private final DishRepository dishRepository;
    private final MongoUserRepository mongoUserRepository;
    private final CategoryRepository categoryRepository;
    private final PatientRepository patientRepository;
    private final MeasurementRepository measurementRepository;
    private final MenuRepository menuRepository;
    private final WeekMealRepository weekMealRepository;
    private final DayMealRepository dayMealRepository;
    private final MealRepository mealRepository;

    @Autowired
    PSQLFoodPropertiesRepository PSQLFoodPropertiesRepository;
    @Autowired
    PSQLCategoryRepository PSQLCategoryRepository;
    @Autowired
    PSQLSubcategoryRepository PSQLSubcategoryRepository;
    @Autowired
    PSQLProductRepository PSQLProductRepository;

    public DbSeeder(MongoProductRepository mongoProductRepository, DishRepository dishRepository, MongoUserRepository mongoUserRepository,
                    CategoryRepository categoryRepository, PatientRepository patientRepository, MeasurementRepository measurementRepository,
                    MenuRepository menuRepository, WeekMealRepository weekMealRepository, DayMealRepository dayMealRepository,
                    MealRepository mealRepository) {
        this.mongoProductRepository = mongoProductRepository;
        this.dishRepository = dishRepository;
        this.mongoUserRepository = mongoUserRepository;
        this.categoryRepository = categoryRepository;
        this.patientRepository = patientRepository;
        this.measurementRepository = measurementRepository;
        this.menuRepository = menuRepository;
        this.weekMealRepository = weekMealRepository;
        this.dayMealRepository = dayMealRepository;
        this.mealRepository = mealRepository;
    }

    @Override
    public void run(String... args) {

        this.mongoUserRepository.deleteAll();

        User user1 = new User("aaa",
                "$2y$12$xQyJdsoamI/19a4p3bgRcOj2KeLpxPWj3.whkTrjz2jzIbO9fnr6m", "imageId");
        mongoUserRepository.save(user1);

        List<ProductExcel> productExcelList = importProductsFromExcel();

        //saveProductsToMongoDb(productExcelList);
        //saveProductsToPsqlDb(productExcelList);
    }

    private List<ProductExcel> importProductsFromExcel() {

        List<ProductExcel> importedProducts = new ArrayList<>();
        List<String> fileImportList = Arrays.asList(
                "ProductData/Stage2.xlsx",
                "ProductData/Stage5.xlsx",
                "ProductData/Stage8.xlsx");

        for (String filePath : fileImportList) {
            try {
                URL url = getClass().getClassLoader().getResource(filePath);
                assert url != null;
                File file = Paths.get(url.toURI()).toFile();

                importedProducts.addAll(Reader.create(ProductExcel.class)
                        .from(file)
                        .start(1)
                        .asList());
            } catch (IllegalArgumentException e) {
                System.out.println("File not found");
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return importedProducts;
    }

    private void saveProductsToMongoDb(List<ProductExcel> importedProducts) {
        this.mongoProductRepository.deleteAll();
        this.categoryRepository.deleteAll();

        Set<String> subcategories = new TreeSet<>();
        String category = "";
        List<Product> products = new ArrayList<>();
        for (ProductExcel productExcel : importedProducts) {
            if (!productExcel.getCategory().equals(category)) {
                if (!category.equals("")) {
                    Category sendCategory = new Category();
                    sendCategory.setCategory(category);
                    sendCategory.setSubcategories(subcategories);
                    try {
                        this.categoryRepository.save(sendCategory);
                    } catch (DuplicateKeyException e) {
                        e.printStackTrace();
                    }
                }
                subcategories.clear();
                category = productExcel.getCategory();
            }
            subcategories.add(productExcel.getSubcategory());
            products.add(new Product(productExcel));
        }
        if (!category.equals("")) {
            Category sendCategory = new Category();
            sendCategory.setCategory(category);
            sendCategory.setSubcategories(subcategories);
            try {
                this.categoryRepository.save(sendCategory);
            } catch (DuplicateKeyException e) {
                e.printStackTrace();
            }
        }

        this.mongoProductRepository.saveAll(products);
    }

    private void saveProductsToPsqlDb(List<ProductExcel> importedProducts) {

        this.PSQLProductRepository.deleteAll();
        this.PSQLFoodPropertiesRepository.deleteAll();
        this.PSQLSubcategoryRepository.deleteAll();
        this.PSQLCategoryRepository.deleteAll();


        for (ProductExcel productExcel : importedProducts) {
            com.springboot.dietapplication.model.psql.product.Category category =
                    new com.springboot.dietapplication.model.psql.product.Category(productExcel.getCategory());
            try {
                this.PSQLCategoryRepository.save(category);
            } catch (DataIntegrityViolationException e) {
                com.springboot.dietapplication.model.psql.product.Category actualCategory =
                        this.PSQLCategoryRepository.getCategoryByNameLike(category.getName());
                if (actualCategory != null)
                    category.setId(actualCategory.getId());
            }

            Subcategory subcategory = new Subcategory(category, productExcel.getSubcategory());
            try {
                this.PSQLSubcategoryRepository.save(subcategory);
            } catch (DataIntegrityViolationException e) {
                Subcategory actualSubcategory = this.PSQLSubcategoryRepository.getSubcategoryByName(subcategory.getName());
                if (actualSubcategory != null)
                    subcategory.setId(actualSubcategory.getId());
            }

            FoodProperties foodProperties = new FoodProperties(productExcel);
            try {
                PSQLFoodPropertiesRepository.save(foodProperties);
            } catch (DataIntegrityViolationException e) { }

            com.springboot.dietapplication.model.psql.product.Product product = new com.springboot.dietapplication.model.psql.product.Product(productExcel);
            product.setCategoryId(category.getId());
            product.setSubcategoryId(subcategory.getId());
            product.setFoodProperties(foodProperties);
            try {
                PSQLProductRepository.save(product);
            } catch (DataIntegrityViolationException e) { }

        }

    }
}
