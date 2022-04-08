package com.springboot.dietapplication.model.psql.menu;

import java.io.Serializable;
import java.util.Objects;

public class PsqlMenuProductKey implements Serializable {

    private static final long serialVersionUID = -2801395378838907004L;

    private Long productId;
    private Long mealId;
    private Long menuId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PsqlMenuProductKey that = (PsqlMenuProductKey) o;
        return productId.equals(that.productId) && mealId.equals(that.mealId) && menuId.equals(that.menuId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, mealId, menuId);
    }
}