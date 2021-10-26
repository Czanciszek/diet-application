package com.springboot.dietapplication.repository;

import com.springboot.dietapplication.model.psql.menu.PsqlFoodTypeMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PsqlFoodTypeMenuRepository extends JpaRepository<PsqlFoodTypeMenu, Long> {

    List<PsqlFoodTypeMenu> findPsqlFoodTypeMenuByMenuId(long id);

}
