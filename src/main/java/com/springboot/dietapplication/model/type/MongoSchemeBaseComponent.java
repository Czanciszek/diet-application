package com.springboot.dietapplication.model.type;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class MongoSchemeBaseComponent implements Serializable {

    @Serial
    private static final long serialVersionUID = 6918603749943343516L;

    private Float grams;
    private String productId;

    public MongoSchemeBaseComponent(SchemeBaseComponent baseComponent) {
        if (baseComponent != null) {
            this.grams = baseComponent.getGrams();
            this.productId = baseComponent.getProductId();
        }
    }
}
