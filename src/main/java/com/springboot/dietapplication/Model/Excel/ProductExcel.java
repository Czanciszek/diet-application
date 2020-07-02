package com.springboot.dietapplication.Model.Excel;

import io.github.biezhi.excel.plus.annotation.ExcelColumn;

public class ProductExcel {

    @ExcelColumn(title = "category", index = 0)
    private String category;

    @ExcelColumn(title = "subcategory", index = 1)
    private String subcategory;

    public String getCategory() {
        return category;
    }

    public String getSubcategory() {
        return subcategory;
    }
}
