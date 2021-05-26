package com.springboot.dietapplication.repository.psql;

import com.springboot.dietapplication.model.psql.menu.PsqlMealTypeMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PsqlMealTypeMenuRepository extends JpaRepository<PsqlMealTypeMenu, Long> {

    List<PsqlMealTypeMenu> findPsqlMealTypeMenuByMenuId(long id);

}
