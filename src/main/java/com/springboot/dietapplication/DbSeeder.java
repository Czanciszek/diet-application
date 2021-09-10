package com.springboot.dietapplication;

import com.springboot.dietapplication.model.excel.ProductExcel;
import com.springboot.dietapplication.model.mongo.user.User;
import com.springboot.dietapplication.repository.mongo.*;
import com.springboot.dietapplication.repository.psql.*;
import com.springboot.dietapplication.service.mongo.*;
import com.springboot.dietapplication.service.psql.*;
import io.github.biezhi.excel.plus.Reader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;

@Component
public class DbSeeder implements CommandLineRunner {

    private final MongoUserRepository mongoUserRepository;
    private final MongoProductRepository mongoProductRepository;
    private final MongoCategoryRepository categoryRepository;

    private final MongoDataService mongoDataService;
    private final MongoMenuService mongoMenuService;
    private final MongoWeekMealService mongoWeekMealService;
    private final MongoDayMealService mongoDayMealService;
    private final MongoMealService mongoMealService;

    private final MongoProductService mongoProductService;
    public static long timeMillis = 0;

    @Autowired
    PsqlFoodPropertiesRepository PSQLFoodPropertiesRepository;
    @Autowired
    PsqlCategoryRepository PSQLCategoryRepository;
    @Autowired
    PsqlProductRepository PSQLProductRepository;
    @Autowired
    PsqlProductDishRepository psqlProductDishRepository;
    @Autowired
    PsqlProductMealRepository psqlProductMealRepository;
    @Autowired
    PsqlMenuRepository psqlMenuRepository;
    @Autowired
    PsqlFoodTypeMenuRepository psqlFoodTypeMenuRepository;
    @Autowired
    PsqlDayMealRepository psqlDayMealRepository;
    @Autowired
    PsqlWeekMealRepository psqlWeekMealRepository;
    @Autowired
    PsqlMealRepository psqlMealRepository;

    @Autowired
    PsqlDataService psqlDataService;
    @Autowired
    PsqlMenuService psqlMenuService;
    @Autowired
    PsqlWeekMealService psqlWeekMealService;
    @Autowired
    PsqlDayMealService psqlDayMealService;
    @Autowired
    PsqlMealService psqlMealService;
    @Autowired
    PsqlProductService psqlProductService;

    public DbSeeder(MongoUserRepository mongoUserRepository,
                    MongoProductRepository mongoProductRepository,
                    MongoCategoryRepository categoryRepository,
                    MongoDataService mongoDataService,
                    MongoMenuService mongoMenuService,
                    MongoWeekMealService mongoWeekMealService,
                    MongoDayMealService mongoDayMealService,
                    MongoMealService mongoMealService,
                    MongoProductService mongoProductService) {
        this.mongoUserRepository = mongoUserRepository;
        this.mongoProductRepository = mongoProductRepository;
        this.categoryRepository = categoryRepository;
        this.mongoDataService = mongoDataService;
        this.mongoMenuService = mongoMenuService;
        this.mongoWeekMealService = mongoWeekMealService;
        this.mongoDayMealService = mongoDayMealService;
        this.mongoMealService = mongoMealService;
        this.mongoProductService = mongoProductService;
    }

    @Override
    public void run(String... args) {
        setUser();

        List<String> prodFilePaths = new ArrayList<>(Arrays.asList("2", "5", "8"));
        List<String> testFilePaths = new ArrayList<>(Arrays.asList("1", "100", "1000", "10000", "50000", "100000"));

        boolean isTesting = false;
        int testTimes = 10;

        String path = isTesting ? "ProductData/StageTest" : "ProductData/Stage";
        List<String> filePaths = isTesting ? testFilePaths : prodFilePaths;
        int times = isTesting ? testTimes : 1;

        Reader<ProductExcel> reader = Reader.create(ProductExcel.class);
        initTime();
        for (String filePath : filePaths) {

            String file = path + filePath + ".xlsx";
            checkTime("TESTING " + file);
            System.out.println("-----------------------------------------------------------------------");
            List<ProductExcel> productExcelList = importProductsFromExcel(file, reader);

            for (int i = 0; i < times; i++) {
                //manageProductsData(productExcelList);
            }
        }

    }

    private void setUser() {
        mongoUserRepository.deleteAll();

        User user1 = new User("aaa",
                "$2y$12$xQyJdsoamI/19a4p3bgRcOj2KeLpxPWj3.whkTrjz2jzIbO9fnr6m", "imageId");
        mongoUserRepository.save(user1);
    }

    private void manageProductsData(List<ProductExcel> productExcelList) {
        insertData(productExcelList, true, true);
        createBackup(true, true);
        clearData(true, true);
        restoreBackup(true, true);
        getData(true, true);
        System.out.println("--------------------------");
    }

    private void createBackup(boolean mongo, boolean psql) {
        if (mongo) {
            checkTime("Create Mongo Backup - Start");
            mongoDataService.createBackup();
            checkTime("Create Mongo Backup - Finish");
        }

        if (psql) {
            checkTime("Create Psql Backup - Start");
            psqlDataService.createBackup();
            checkTime("Create Psql Backup - Finish");
        }
    }

    private void restoreBackup(boolean mongo, boolean psql) {
        if (mongo) {
            checkTime("Restore Mongo Backup - Start");
            mongoDataService.restoreBackup();
            checkTime("Restore Mongo Backup - Finish");
        }

        if (psql) {
            checkTime("Restore Psql Backup - Start");
            psqlDataService.restoreBackup();
            checkTime("Restore Psql Backup - Finish");
        }
    }

    private void clearData(boolean mongo, boolean psql) {
        if (mongo) {
            checkTime("Clear Mongo - Start");
            mongoDataService.clearDatabase();
            checkTime("Clear Mongo - Finish");
        }

        if (psql) {
            checkTime("Clear PSQL - Start");
            psqlDataService.clearDatabase();
            checkTime("Clear PSQL - Finish");
        }
    }

    private void insertData(List<ProductExcel> productExcelList, boolean mongo, boolean psql) {
        if (mongo) {
            checkTime("Save to Mongo - Start");
            mongoDataService.saveProducts(productExcelList);
            checkTime("Save to Mongo - Finish");
        }

        if (psql) {
            checkTime("Save to PSQL - Start");
            psqlDataService.saveProducts(productExcelList);
            checkTime("Save to PSQL - Finish");
        }
    }

    private void getData(boolean mongo, boolean psql) {
        if (mongo) {
            checkTime("Get products Mongo - Start");
            mongoProductService.getAll();
            checkTime("Get products Mongo - Finish");
        }

        if (psql) {
            checkTime("Get products PSQL - Start");
            psqlProductService.getAll();
            checkTime("Get products PSQL - Finish");
        }
    }

    private void initTime() {
        timeMillis = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeMillis);

        int h = calendar.get(Calendar.HOUR_OF_DAY);
        int m = calendar.get(Calendar.MINUTE);
        int s = calendar.get(Calendar.SECOND);
        int ms = calendar.get(Calendar.MILLISECOND);
        System.out.println("Time: " + h + ":" + m + ":" + s + "." + ms + " - Start");
    }

    private void checkTime(String checkMoment) {
        long currentMillis = System.currentTimeMillis();
        System.out.println((currentMillis - timeMillis) + "ms - " + checkMoment);
        timeMillis = currentMillis;
    }

    private File importFile(String filePath) {
        try {
            URL url = getClass().getClassLoader().getResource(filePath);
            assert url != null;

            return Paths.get(url.toURI()).toFile();
        } catch (IllegalArgumentException e) {
            System.out.println("File not found");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new File(filePath);
    }

    private List<ProductExcel> importProductsFromExcel(String filePath, Reader<ProductExcel> reader) {
        File file = importFile(filePath);
        checkTime("importProductsFromExcel - Read file - Start");
        List<ProductExcel> productExcelList = reader
                .from(file)
                .start(1)
                .asList();
        checkTime("importProductsFromExcel - Read file - Finish");
        return productExcelList;
    }

}