package com.springboot.dietapplication.repository;

import com.springboot.dietapplication.model.psql.dish.PsqlDish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishRepository extends JpaRepository<PsqlDish, Long> {

    List<PsqlDish> findByMenuIdIsNull();

    List<PsqlDish> findByMenuId(Long menuId);
}
