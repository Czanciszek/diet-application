package com.springboot.dietapplication.model.psql.dish;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class PsqlDishUsageKey implements Serializable {

    @Serial
    private static final long serialVersionUID = -2801395378838907004L;

    private Long menuId;
    private Long dishId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PsqlDishUsageKey that = (PsqlDishUsageKey) o;
        return menuId.equals(that.menuId) && dishId.equals(that.dishId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuId, dishId);
    }
}