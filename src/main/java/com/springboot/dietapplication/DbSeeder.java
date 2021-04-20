package com.springboot.dietapplication;

import com.springboot.dietapplication.model.product.Category;
import com.springboot.dietapplication.model.product.Product;
import com.springboot.dietapplication.model.excel.ProductExcel;
import com.springboot.dietapplication.model.user.User;
import com.springboot.dietapplication.repository.*;
import io.github.biezhi.excel.plus.Reader;
import org.joda.time.DateTime;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;

@Component
public class DbSeeder implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final DishRepository dishRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final PatientRepository patientRepository;
    private final MeasurementRepository measurementRepository;
    private final MenuRepository menuRepository;
    private final WeekMealRepository weekMealRepository;
    private final DayMealRepository dayMealRepository;
    private final MealRepository mealRepository;

    public DbSeeder(ProductRepository productRepository, DishRepository dishRepository, UserRepository userRepository,
                    CategoryRepository categoryRepository, PatientRepository patientRepository, MeasurementRepository measurementRepository,
                    MenuRepository menuRepository, WeekMealRepository weekMealRepository, DayMealRepository dayMealRepository,
                    MealRepository mealRepository) {
        this.productRepository = productRepository;
        this.dishRepository = dishRepository;
        this.userRepository = userRepository;
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

        this.userRepository.deleteAll();

        User user1 = new User("aaa",
                "$2y$12$xQyJdsoamI/19a4p3bgRcOj2KeLpxPWj3.whkTrjz2jzIbO9fnr6m", "imageId");
        userRepository.save(user1);

        //importProductsFromExcel();
    }

    private void importProductsFromExcel() {
        this.productRepository.deleteAll();
        this.categoryRepository.deleteAll();

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

                importedProducts = Reader.create(ProductExcel.class)
                        .from(file)
                        .start(1)
                        .asList();
            } catch (IllegalArgumentException e) {
                System.out.println("File not found");
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            Set<String> subcategories = new TreeSet<>();

            String category = "";
            List<Product> products = new ArrayList<>();
            for (ProductExcel productExcel : importedProducts) {
                if (!productExcel.getCategory().equals(category)) {
                    if (!category.equals("")) {
                        Category sendCategory = new Category();
                        sendCategory.setCategory(category);
                        sendCategory.setSubcategories(subcategories);
                        this.categoryRepository.save(sendCategory);
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
                this.categoryRepository.save(sendCategory);
            }

            this.productRepository.saveAll(products);
        }
    }
}
