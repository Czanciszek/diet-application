package com.springboot.dietapplication.MongoRepository;

import com.springboot.dietapplication.Model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    List<Product> findByNameLike(String name);

    List<Product> findByNameRegex(String name);

}