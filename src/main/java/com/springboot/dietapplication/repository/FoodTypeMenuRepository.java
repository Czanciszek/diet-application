package com.springboot.dietapplication.repository;

import com.springboot.dietapplication.model.psql.menu.PsqlFoodTypeMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import java.util.List;

@Deprecated(since = "0.1.0", forRemoval = true)
@Repository
public interface FoodTypeMenuRepository extends JpaRepository<PsqlFoodTypeMenu, Long> {

    List<PsqlFoodTypeMenu> findPsqlFoodTypeMenuByMenuId(long id);

    @Transactional
    List<PsqlFoodTypeMenu> deletePsqlFoodTypeMenuByMenuId(Long mealId);

}
