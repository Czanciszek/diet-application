package com.springboot.dietapplication.Model.Excel;

import io.github.biezhi.excel.plus.annotation.ExcelColumn;

public class ProductExcel {

    @ExcelColumn(title = "category", index = 0)
    private String category;

    @ExcelColumn(title = "subcategory", index = 1)
    private String subcategory;

    @ExcelColumn(title = "name", index = 3)
    private String name;

    @ExcelColumn(title = "energyValue", index = 4)
    private int energyValue;

    @ExcelColumn(title = "proteins", index = 5)
    private float proteins;

    @ExcelColumn(title = "fats", index = 6)
    private float fats;

    @ExcelColumn(title = "carbohydrates", index = 11)
    private float carbohydrates;

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
