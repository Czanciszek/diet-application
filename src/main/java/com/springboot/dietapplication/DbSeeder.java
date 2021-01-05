package com.springboot.dietapplication;

import com.springboot.dietapplication.model.base.DocRef;
import com.springboot.dietapplication.model.base.Header;
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

    private ProductRepository productRepository;
    private DishRepository dishRepository;
    private UserRepository userRepository;
    private CategoryRepository categoryRepository;
    private PatientRepository patientRepository;
    private MeasurementRepository measurementRepository;
    private MenuRepository menuRepository;
    private WeekMealRepository weekMealRepository;
    private DayMealRepository dayMealRepository;
    private MealRepository mealRepository;

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

        this.productRepository.deleteAll();
        this.userRepository.deleteAll();

        User user1 = new User("aaa",
                "$2y$12$xQyJdsoamI/19a4p3bgRcOj2KeLpxPWj3.whkTrjz2jzIbO9fnr6m", "imageId");
        userRepository.save(user1);

        User user = new User("name", "password", "imageId");
        Header header = new Header();
        header.setCreatedBy(DocRef.fromDoc(user));

        List<ProductExcel> importedProducts = new ArrayList<>();

        List<String> fileImportList = Arrays.asList(
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

                Product product = new Product(productExcel);
                long currentTime = new DateTime().getMillis();
                product.setHeader(header);
                product.getHeader().setCreatedEpochMillis(currentTime);
                products.add(product);
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
