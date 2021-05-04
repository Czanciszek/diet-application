package com.springboot.dietapplication.model.psql.product;

import javax.persistence.*;

@Entity
@Table(name = "categories")
public class PsqlCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "category")
    private String category;

    @Column(name = "subcategory")
    private String subcategory;

    public PsqlCategory() {
    }

    public PsqlCategory(String category, String subcategory) {
        this.category = category;
        this.subcategory = subcategory;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }
}
