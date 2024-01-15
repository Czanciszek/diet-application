package com.springboot.dietapplication;

import com.springboot.dietapplication.model.excel.ProductExcel;
import com.springboot.dietapplication.service.*;
import io.github.biezhi.excel.plus.Reader;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;

@Component
@NoArgsConstructor
public class DbSeeder implements CommandLineRunner {

    @Autowired
    DataService dataService;

    @Override
    public void run(String... args) {

        if (args.length < 2) {
            return;
        }

        List<String> prodFilePaths = new ArrayList<>(Arrays.asList("2", "5", "8"));

        String path = "ProductData/Stage";

        Reader<ProductExcel> reader = Reader.create(ProductExcel.class);
        for (String filePath : prodFilePaths) {

            String file = path + filePath + ".xlsx";
            List<ProductExcel> productExcelList = importProductsFromExcel(file, reader);
            manageProductsData(productExcelList);
        }

    }

    private void manageProductsData(List<ProductExcel> productExcelList) {
        insertData(productExcelList);
        createBackup();
        clearData();
        restoreBackup();
        System.out.println("--------------------------");
    }

    private void createBackup() {
        dataService.createBackup();
    }

    private void restoreBackup() {
        dataService.restoreBackup();
    }

    private void clearData() {
        dataService.clearDatabase();
    }

    private void insertData(List<ProductExcel> productExcelList) {
        dataService.saveProducts(productExcelList);
    }

    private File importFile(String filePath) {
        try {
            URL url = getClass().getClassLoader().getResource(filePath);
            assert url != null;

            return Paths.get(url.toURI()).toFile();
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