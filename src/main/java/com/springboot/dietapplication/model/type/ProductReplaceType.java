package com.springboot.dietapplication.model.type;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductReplaceType {

    private String menuId;
    private String oldProductId;
    private String newProductId;

}