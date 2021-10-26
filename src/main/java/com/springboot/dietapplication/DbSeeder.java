package com.springboot.dietapplication;

import com.springboot.dietapplication.model.excel.ProductExcel;
import com.springboot.dietapplication.repository.*;
import com.springboot.dietapplication.service.*;
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

    public DbSeeder() {

    }

    @Override
    public void run(String... args) {
//        setUser();
//
//        List<String> prodFilePaths = new ArrayList<>(Arrays.asList("2", "5", "8"));
//        List<String> testFilePaths = new ArrayList<>(Arrays.asList("1", "100", "1000", "10000", "50000", "100000"));
//
//        boolean isTesting = false;
//        int testTimes = 10;
//
//        String path = isTesting ? "ProductData/StageTest" : "ProductData/Stage";
//        List<String> filePaths = isTesting ? testFilePaths : prodFilePaths;
//        int times = isTesting ? testTimes : 1;
//
//        Reader<ProductExcel> reader = Reader.create(ProductExcel.class);
//        initTime();
//        for (String filePath : filePaths) {
//
//            String file = path + filePath + ".xlsx";
//            checkTime("TESTING " + file);
//            System.out.println("-----------------------------------------------------------------------");
//            List<ProductExcel> productExcelList = importProductsFromExcel(file, reader);
//
//            for (int i = 0; i < times; i++) {
//                //manageProductsData(productExcelList);
//            }
//        }

    }

    private void setUser() {


//        PsqlUser user1 = new PsqlUser("aaa",
//                "$2y$12$xQyJdsoamI/19a4p3bgRcOj2KeLpxPWj3.whkTrjz2jzIbO9fnr6m", "imageId");

    }

    private void manageProductsData(List<ProductExcel> productExcelList) {
        insertData(productExcelList);
        createBackup();
        clearData();
        restoreBackup();
        System.out.println("--------------------------");
    }

    private void createBackup() {
        psqlDataService.createBackup();
    }

    private void restoreBackup() {
        psqlDataService.restoreBackup();
    }

    private void clearData() {
        psqlDataService.clearDatabase();
    }

    private void insertData(List<ProductExcel> productExcelList) {
        psqlDataService.saveProducts(productExcelList);
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
        return reader
                .from(file)
                .start(1)
                .asList();
    }

}