package com.springboot.dietapplication;

import com.springboot.dietapplication.model.mongo.product.MongoCategory;
import com.springboot.dietapplication.model.mongo.product.MongoProduct;
import com.springboot.dietapplication.model.excel.ProductExcel;
import com.springboot.dietapplication.model.mongo.properties.MongoFoodProperties;
import com.springboot.dietapplication.model.mongo.user.User;
import com.springboot.dietapplication.model.psql.product.PsqlCategory;
import com.springboot.dietapplication.model.psql.product.PsqlProduct;
import com.springboot.dietapplication.model.psql.properties.PsqlFoodProperties;
import com.springboot.dietapplication.repository.mongo.*;
import com.springboot.dietapplication.repository.psql.*;
import io.github.biezhi.excel.plus.Reader;
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

    private final MongoUserRepository mongoUserRepository;
    private final MongoProductRepository mongoProductRepository;
    private final MongoFoodPropertiesRepository mongoFoodPropertiesRepository;

    private final DishRepository dishRepository;

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
    PSQLProductRepository PSQLProductRepository;

    public DbSeeder(MongoUserRepository mongoUserRepository,
                    MongoProductRepository mongoProductRepository,
                    MongoFoodPropertiesRepository mongoFoodPropertiesRepository,
                    DishRepository dishRepository,
                    CategoryRepository categoryRepository,
                    PatientRepository patientRepository,
                    MeasurementRepository measurementRepository,
                    MenuRepository menuRepository,
                    WeekMealRepository weekMealRepository,
                    DayMealRepository dayMealRepository,
                    MealRepository mealRepository) {
        this.mongoUserRepository = mongoUserRepository;
        this.mongoProductRepository = mongoProductRepository;
        this.mongoFoodPropertiesRepository = mongoFoodPropertiesRepository;
        this.dishRepository = dishRepository;
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

        List<MongoProduct> products = new ArrayList<>();
        for (ProductExcel productExcel : importedProducts) {
            MongoProduct product = new MongoProduct(productExcel);

            MongoFoodProperties foodProperties = new MongoFoodProperties(productExcel);
            this.mongoFoodPropertiesRepository.save(foodProperties);

            MongoCategory category = new MongoCategory(
                    productExcel.getCategory(),
                    productExcel.getSubcategory()
            );

            try {
                this.categoryRepository.save(category);
            } catch (DuplicateKeyException e) {
                MongoCategory actualCategory =
                        this.categoryRepository.getCategoryByCategoryAndSubcategory(
                                category.getCategory(),
                                category.getSubcategory());
                category.setId(actualCategory.getId());
            }

            product.setFoodPropertiesId(foodProperties.getId());
            product.setCategoryId(category.getId());
            products.add(product);
        }

        this.mongoProductRepository.saveAll(products);
    }

    private void saveProductsToPsqlDb(List<ProductExcel> importedProducts) {

        this.PSQLProductRepository.deleteAll();
        this.PSQLFoodPropertiesRepository.deleteAll();
        this.PSQLCategoryRepository.deleteAll();

        for (ProductExcel productExcel : importedProducts) {
            PsqlCategory category =
                    new PsqlCategory(
                            productExcel.getCategory(),
                            productExcel.getSubcategory()
                    );
            try {
                this.PSQLCategoryRepository.save(category);
            } catch (DataIntegrityViolationException e) {
                PsqlCategory actualCategory =
                        this.PSQLCategoryRepository.getCategoryByCategoryAndSubcategory(
                                category.getCategory(),
                                category.getSubcategory());
                category.setId(actualCategory.getId());
            }

            PsqlFoodProperties foodProperties = new PsqlFoodProperties(productExcel);
            try {
                PSQLFoodPropertiesRepository.save(foodProperties);
            } catch (DataIntegrityViolationException e) { }

            PsqlProduct psqlProduct =
                    new PsqlProduct(productExcel);
            psqlProduct.setCategoryId(category.getId());
            psqlProduct.setFoodPropertiesId(foodProperties.getId());
            try {
                PSQLProductRepository.save(psqlProduct);
            } catch (DataIntegrityViolationException e) { }

        }

    }
}
