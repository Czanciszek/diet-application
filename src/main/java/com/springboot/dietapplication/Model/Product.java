package com.springboot.dietapplication.Model;

import com.springboot.dietapplication.Model.Excel.ProductExcel;
import io.github.biezhi.excel.plus.annotation.ExcelColumn;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Products")
public class Product {
    @Id
    private String id;
    private String category;
    private String subcategory;
    private String name;
    private int energyValue;
    private float proteins;
    private float fats;
    private float carbohydrates;

    protected Product() {

    }

    public Product(String id, String category, String subcategory, String name,
                   int energyValue, float proteins, float fats, float carbohydrates) {
        this.id = id;
        this.category = category;
        this.subcategory = subcategory;
        this.name = name;
        this.energyValue = energyValue;
        this.proteins = proteins;
        this.fats = fats;
        this.carbohydrates = carbohydrates;
    }

    public Product(ProductExcel productExcel) {
        this.category = productExcel.getCategory();
        this.subcategory = productExcel.getSubcategory();
        this.name = productExcel.getName();
        this.energyValue = productExcel.getEnergyValue();
        this.proteins = productExcel.getProteins();
        this.fats = productExcel.getFats();
        this.carbohydrates = productExcel.getCarbohydrates();
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

    public String getName() {
        return name;
    }

    public int getEnergyValue() {
        return energyValue;
    }

    public float getProteins() {
        return proteins;
    }

    public float getFats() {
        return fats;
    }

    public float getCarbohydrates() {
        return carbohydrates;
    }
}
