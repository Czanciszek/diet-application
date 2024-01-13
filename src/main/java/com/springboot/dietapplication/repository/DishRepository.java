package com.springboot.dietapplication.repository;

import com.springboot.dietapplication.model.psql.dish.PsqlDish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Deprecated(since = "0.1.0", forRemoval = true)
@Repository
public interface DishRepository extends JpaRepository<PsqlDish, Long> {

    List<PsqlDish> findAllWhereIsSystemOrByUserId(Long userId);

}
