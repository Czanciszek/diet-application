package com.springboot.dietapplication.model.excel;

import io.github.biezhi.excel.plus.annotation.ExcelColumn;
import lombok.Getter;

@Getter
public class ProductReplacementsExcel {

    @ExcelColumn(title = "_id", index = 0)
    private String id;

    @ExcelColumn(title = "energyValue", index = 1)
    private int energyValue;

    @ExcelColumn(title = "proteins", index = 2)
    private float proteins;

    @ExcelColumn(title = "fats", index = 3)
    private float fats;

    @ExcelColumn(title = "carbohydrates", index = 4)
    private float carbohydrates;

    @ExcelColumn(title = "name", index = 5)
    private String name;

    @ExcelColumn(title = "proteinsReplacement", index = 6)
    private boolean proteinsReplacement;

    @ExcelColumn(title = "fatsReplacement", index = 7)
    private boolean fatsReplacement;

    @ExcelColumn(title = "carbohydratesReplacement", index = 8)
    private boolean carbohydratesReplacement;

    @ExcelColumn(title = "fibersReplacement", index = 8)
    private boolean fibersReplacement;

    public ProductReplacementsExcel() {
    }
}
