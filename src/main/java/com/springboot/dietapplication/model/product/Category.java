package com.springboot.dietapplication.model.product;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document(collection = "Categories")
public class Category {

    private String category;

    private Set<String> subcategories;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Set<String> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(Set<String> subcategories) {
        this.subcategories = subcategories;
    }
}
