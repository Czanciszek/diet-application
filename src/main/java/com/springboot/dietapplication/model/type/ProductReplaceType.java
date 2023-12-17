package com.springboot.dietapplication.model.type;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductReplaceType {

    private String menuId;
    private String oldProductId;
    private String newProductId;

    public ProductReplaceType() {}

}