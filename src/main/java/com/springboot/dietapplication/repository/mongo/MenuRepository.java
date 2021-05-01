package com.springboot.dietapplication.repository.mongo;

import com.springboot.dietapplication.model.menu.Menu;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends MongoRepository<Menu, String> {

    List<Menu> findByPatientId(String id);

}
