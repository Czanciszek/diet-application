package com.springboot.dietapplication.utils;

import com.springboot.dietapplication.model.mongo.product.MongoProduct;
import com.springboot.dietapplication.model.type.ProductType;
import com.springboot.dietapplication.repository.mongo.MongoProductRepository;
import com.springboot.dietapplication.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MongoProductRunner {

    @Autowired
    ProductService productService;

    @Autowired
    MongoProductRepository mongoProductRepository;

    public void reloadProductsPSQLtoMongo() {

        mongoProductRepository.deleteAll();

        List<ProductType> productTypeList = productService.getAll();

        List<MongoProduct> mongoProducts = productTypeList
                .stream()
                .map(MongoProduct::new)
                .collect(Collectors.toList());

        String currentDate =  DateFormatter.getInstance().getCurrentDate();
        mongoProducts.forEach(product -> {
            product.setCreationDate(currentDate);
            product.setUpdateDate(currentDate);
        });

        mongoProductRepository.saveAll(mongoProducts);
    }
}
