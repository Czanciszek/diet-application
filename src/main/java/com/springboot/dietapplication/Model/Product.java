package com.springboot.dietapplication.Model;

import com.springboot.dietapplication.Model.Excel.ProductExcel;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Products")
public class Product {
    @Id
    private String id;
    private String category;
    private String subcategory;

    protected Product() {

    }

    public Product(String category, String subcategory) {
        this.category = category;
        this.subcategory = subcategory;
    }

    public Product(ProductExcel productExcel) {
        this.category = productExcel.getCategory();
        this.subcategory = productExcel.getSubcategory();
    }

    public String getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getSubcategory() {
        return subcategory;
    }
}
