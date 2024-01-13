package com.springboot.dietapplication.model.psql.user;

import jakarta.persistence.*;

@Deprecated(since = "0.1.0", forRemoval = true)
@Entity
@Table(name = "user_types")
public class PsqlUserType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

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
