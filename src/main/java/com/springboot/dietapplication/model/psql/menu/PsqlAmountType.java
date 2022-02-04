package com.springboot.dietapplication.model.psql.menu;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "amount_types")
public class PsqlAmountType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    public PsqlAmountType() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
