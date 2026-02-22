package com.springboot.dietapplication.model.type;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class SchemeBaseComponent implements Serializable {

    @Serial
    private static final long serialVersionUID = 6918603749943343516L;

    private Float grams;
    private String productId;

    public SchemeBaseComponent(MongoSchemeBaseComponent mongoSchemeBaseComponent) {
        if (mongoSchemeBaseComponent != null) {
            this.grams = mongoSchemeBaseComponent.getGrams();
            this.productId = mongoSchemeBaseComponent.getProductId();
        }
    }
}
