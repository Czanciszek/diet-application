package com.springboot.dietapplication;

import com.springboot.dietapplication.model.mongo.MongoProduct;
import com.springboot.dietapplication.model.type.ProductType;
import com.springboot.dietapplication.repository.mongo.MongoProductRepository;
import com.springboot.dietapplication.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@EnableMongoRepositories
public class DietApplication {

    public static void main(String[] args) {
        SpringApplication.run(DietApplication.class, args);
    }

    @Autowired
    ProductService productService;

    @Autowired
    MongoProductRepository mongoProductRepository;

    @Bean
    CommandLineRunner runner(MongoProductRepository repository) {
        return args -> {
            try {
//                mongoProductRepository.deleteAll();
//
//                List<ProductType> productTypeList = productService.getAll();
//
//                List<MongoProduct> mongoProducts = productTypeList
//                        .stream()
//                        .map(MongoProduct::new)
//                        .collect(Collectors.toList());
//
//                mongoProductRepository.saveAll(mongoProducts);
            } catch (Exception e) {
                e.printStackTrace();
            }

        };
    }

}
