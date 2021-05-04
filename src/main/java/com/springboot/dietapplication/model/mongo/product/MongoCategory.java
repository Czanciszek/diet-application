package com.springboot.dietapplication.model.mongo.product;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Categories")
public class MongoCategory {

    private String id;

    private String category;

    private String subcategory;

    public MongoCategory() {
    }

    public MongoCategory(String category, String subcategory) {
        this.category = category;
        this.subcategory = subcategory;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }
}
