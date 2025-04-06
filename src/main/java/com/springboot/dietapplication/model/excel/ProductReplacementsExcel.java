package com.springboot.dietapplication.model.excel;

import io.github.biezhi.excel.plus.annotation.ExcelColumn;
import lombok.Getter;

@Getter
public class ProductReplacementsExcel {

    @ExcelColumn(title = "_id", index = 1)
    private String id;

    @ExcelColumn(title = "energyValue", index = 2)
    private int energyValue;

    @ExcelColumn(title = "proteins", index = 3)
    private float proteins;

    @ExcelColumn(title = "fats", index = 4)
    private float fats;

    @ExcelColumn(title = "carbohydrates", index =5)
    private float carbohydrates;

    @ExcelColumn(title = "name", index = 6)
    private String name;

    @ExcelColumn(title = "proteinsReplacement", index = 7)
    private boolean proteinsReplacement;

    @ExcelColumn(title = "fatsReplacement", index = 8)
    private boolean fatsReplacement;

    @ExcelColumn(title = "carbohydratesReplacement", index = 9)
    private boolean carbohydratesReplacement;

    @ExcelColumn(title = "fibersReplacement", index = 10)
    private boolean fibersReplacement;

    public ProductReplacementsExcel() {
    }
}
