package com.springboot.dietapplication.service;

import com.springboot.dietapplication.model.excel.ProductReplacementsExcel;
import com.springboot.dietapplication.model.type.ProductReplacements;
import com.springboot.dietapplication.repository.MongoProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DataService {

    @Autowired
    MongoProductRepository mongoProductRepository;

    public void updateProductReplacements(List<ProductReplacementsExcel> importedProducts) {

        var allProducts = mongoProductRepository.findAll();

        for (var originProduct: allProducts) {
            var optionalProduct = importedProducts
                    .stream()
                    .filter(p -> p.getId().equals(originProduct.getId()))
                    .findFirst();

            if (optionalProduct.isEmpty())
                continue;

            var product = optionalProduct.get();

            var replacements = new ProductReplacements(
                    product.isProteinsReplacement(),
                    product.isFatsReplacement(),
                    product.isCarbohydratesReplacement(),
                    product.isFibersReplacement()
            );

            originProduct.setReplacements(replacements);

//            String currentDate = DateFormatter.getInstance().getCurrentDate();
//            originProduct.setUpdateDate(currentDate);

            mongoProductRepository.save(originProduct);
        }
    }

    public void clearDatabase() {
    }

    public void createBackup() {
        try {
            List<String> command = Arrays.asList(
                    "\"C:/Program Files/PostgreSQL/11/bin/pg_dump\"",
                    "--dbname=postgresql://postgres:postgres@127.0.0.1:5432/dietapp",
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
                    "--dbname=postgresql://postgres:postgres@127.0.0.1:5432/dietapp"
            );

            Process process = new ProcessBuilder(command).start();
            process.waitFor();
            process.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
