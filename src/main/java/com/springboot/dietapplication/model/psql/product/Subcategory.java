package com.springboot.dietapplication.model.psql.product;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "subcategories")
public class Subcategory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @Column(name = "name")
    private String name;

    public Subcategory() {
    }

    public Subcategory(Category category, String name) {
        this.category = category;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
