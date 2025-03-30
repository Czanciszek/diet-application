package com.springboot.dietapplication.model.type;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductReplacements {

    private boolean proteins;
    private boolean fats;
    private boolean carbohydrates;
    private boolean fibers;

    public ProductReplacements(boolean proteins, boolean fats, boolean carbohydrates, boolean fibers) {
        this.proteins = proteins;
        this.fats = fats;
        this.carbohydrates = carbohydrates;
        this.fibers = fibers;
    }
}
