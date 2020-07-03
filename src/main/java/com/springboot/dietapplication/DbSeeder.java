package com.springboot.dietapplication;

import com.springboot.dietapplication.Model.Product;
import com.springboot.dietapplication.Model.Excel.ProductExcel;
import com.springboot.dietapplication.MongoRepository.ProductRepository;
import io.github.biezhi.excel.plus.Reader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class DbSeeder implements CommandLineRunner {

    private ProductRepository productRepository;

    public DbSeeder(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) {
        List<ProductExcel> importedProducts = new ArrayList<>();

        try {
            URL url = getClass().getClassLoader().getResource("ProductData/Stage8.xlsx");
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

        List<Product> products = new ArrayList<>();
        for (ProductExcel productExcel : importedProducts) {
            Product product = new Product(productExcel);
            products.add(product);
        }

        this.productRepository.deleteAll();
        this.productRepository.saveAll(products);
    }
}
