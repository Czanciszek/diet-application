package com.springboot.dietapplication;

import com.springboot.dietapplication.model.base.DocRef;
import com.springboot.dietapplication.model.base.Header;
import com.springboot.dietapplication.model.menu.*;
import com.springboot.dietapplication.model.patient.Measurement;
import com.springboot.dietapplication.model.patient.Patient;
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
        this.categoryRepository.deleteAll();
        this.patientRepository.deleteAll();
        this.measurementRepository.deleteAll();
        this.mealRepository.deleteAll();
        this.dayMealRepository.deleteAll();
        this.weekMealRepository.deleteAll();
        this.menuRepository.deleteAll();

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

        Patient patient = new Patient();
        patient.setBirthDate(DateTime.parse("1960-05-04").toString());
        patient.setBodyWeight(120);
        patient.setName("Pacjent");
        patient.setSex(false);
        patient.setEmail("email@email.com");
        patient.setNumberPhone("123456789");
        patient.setDietaryPurpose("Schudnąć xD");
        this.patientRepository.save(patient);

        DocRef<Patient> patientDocRef = DocRef.fromDoc(patient);

        Measurement measurement = new Measurement();
        measurement.setMeasurementDate(DateTime.parse("2020-05-04").toString());
        measurement.setPatientDocRef(patientDocRef);
        measurement.setArm(2.0f);
        this.measurementRepository.save(measurement);

        Measurement measurement2 = new Measurement();
        measurement2.setMeasurementDate(DateTime.parse("2020-06-13").toString());
        measurement2.setPatientDocRef(patientDocRef);
        measurement2.setAbdominal(3.0f);
        this.measurementRepository.save(measurement2);

        List<MealType> mealTypes = new ArrayList<>();
        mealTypes.add(MealType.BREAKFEST);
        mealTypes.add(MealType.DINNER);
        mealTypes.add(MealType.SUPPER);

        Menu menu = createMenu(menuRepository);
        menu.setMealTypes(mealTypes);
        menu.setStartDate(DateTime.parse("2020-12-14").toString());
        menu.setEndDate(DateTime.parse("2020-12-20").toString());
        menu.setMeasurementDocRef(DocRef.fromDoc(measurement));

        WeekMeal weekMeal = createWeekMeal(weekMealRepository);
        DayMeal dayMeal = createDayMeal(dayMealRepository);

        Meal meal = createMeal(mealRepository);
        Meal meal2 = createMeal(mealRepository);

        meal.setDayMealDocRef(DocRef.fromDoc(dayMeal));
        meal2.setDayMealDocRef(DocRef.fromDoc(dayMeal));
        List<String> mealList = new ArrayList<>();
        mealList.add(meal.getId());
        mealList.add(meal2.getId());
        dayMeal.setMealList(mealList);
        dayMeal.setWeekMealDocRef(DocRef.fromDoc(weekMeal));
        dayMeal.setMenuDocRef(DocRef.fromDoc(menu));

        List<String> dayMeals = new ArrayList<>();
        dayMeals.add(dayMeal.getId());
        weekMeal.setDayMealList(dayMeals);
        weekMeal.setMenuDocRef(DocRef.fromDoc(menu));

        List<String> weekMeals = new ArrayList<>();
        weekMeals.add(weekMeal.getId());
        menu.setWeekMealList(weekMeals);
        menu.setPatientDocRef(patientDocRef);

        mealRepository.save(meal);
        mealRepository.save(meal2);
        dayMealRepository.save(dayMeal);
        weekMealRepository.save(weekMeal);
        menuRepository.save(menu);

        Menu menu2 = new Menu(menu);
        menu2.setMeasurementDocRef(null);
        menuRepository.save(menu2);
    }

    private Meal createMeal(MealRepository mealRepository) {
        Meal meal = new Meal();
        mealRepository.save(meal);
        return meal;
    }

    private DayMeal createDayMeal(DayMealRepository dayMealRepository) {
        DayMeal dayMeal = new DayMeal();
        dayMealRepository.save(dayMeal);
        return dayMeal;
    }

    private WeekMeal createWeekMeal(WeekMealRepository weekMealRepository) {
        WeekMeal weekMeal = new WeekMeal();
        weekMealRepository.save(weekMeal);
        return weekMeal;
    }

    private Menu createMenu(MenuRepository menuRepository) {
        Menu menu = new Menu();
        menuRepository.save(menu);
        return menu;
    }

}
