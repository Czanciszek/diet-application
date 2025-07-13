package com.springboot.dietapplication.model.type;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class CategoryType implements Serializable, Comparable<CategoryType> {

    @Serial
    private static final long serialVersionUID = 6325466471465045212L;

    private String id;
    private String category;
    private String subcategory;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof CategoryType categoryType))
            return false;

        if (id == null || categoryType.id == null)
            return false;

        return id.equals((categoryType).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(CategoryType o) {
        return this.getCategory().compareToIgnoreCase(o.getCategory());
    }
}
