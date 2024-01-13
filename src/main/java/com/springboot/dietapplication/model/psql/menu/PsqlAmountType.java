package com.springboot.dietapplication.model.psql.menu;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Deprecated(since = "0.1.0", forRemoval = true)
@Entity
@Table(name = "amount_types")
public class PsqlAmountType implements Serializable {

    @Serial
    private static final long serialVersionUID = -668880590166875609L;

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
