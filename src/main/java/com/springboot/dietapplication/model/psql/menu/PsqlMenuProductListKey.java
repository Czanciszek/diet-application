package com.springboot.dietapplication.model.psql.menu;

import java.io.Serializable;
import java.util.Objects;

public class PsqlMenuProductListKey implements Serializable {

    private long productId;
    private long mealId;
    private long menuId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PsqlMenuProductListKey that = (PsqlMenuProductListKey) o;
        return productId == that.productId && mealId == that.mealId && menuId == that.menuId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, mealId, menuId);
    }
}