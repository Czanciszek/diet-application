package com.springboot.dietapplication.utils;

import com.springboot.dietapplication.model.mongo.menu.MongoMenu;
import com.springboot.dietapplication.model.type.MenuType;
import com.springboot.dietapplication.repository.mongo.MongoMenuRepository;
import com.springboot.dietapplication.service.MenuService;
import com.springboot.dietapplication.service.MenuServiceV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MongoMenuRunner {

    @Autowired
    MenuService menuService;

    @Autowired
    MenuServiceV2 menuServiceV2;

    @Autowired
    MongoMenuRepository mongoMenuRepository;

    public void reloadMenusPSQLtoMongo() {

        long start = System.currentTimeMillis();

        mongoMenuRepository.deleteAll();
        long deleteAll = System.currentTimeMillis();

        List<MenuType> menuTypeList = menuService.getAll();
        long getPSQLMenus = System.currentTimeMillis();

        String currentDate = DateFormatter.getInstance().getCurrentDate();

        List<MongoMenu> mongoMenus = menuTypeList
                .stream()
                .map(m -> {
                    MongoMenu mongoMenu = new MongoMenu(m);

                    Instant instantStartDate = Instant.parse(m.getStartDate());
                    LocalDate startLocalDate = Instant
                            .ofEpochMilli(instantStartDate.toEpochMilli())
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                    mongoMenu.setStartDate(startLocalDate.toString());

                    Instant instantEndDate = Instant.parse(m.getEndDate());
                    LocalDate endLocalDate = Instant
                            .ofEpochMilli(instantEndDate.toEpochMilli())
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                    mongoMenu.setEndDate(endLocalDate.toString());

                    return mongoMenu;
                })
                .collect(Collectors.toList());

        mongoMenus.forEach(menu -> {
            menu.setCreationDate(currentDate);
            menu.setUpdateDate(currentDate);
        });
        long convertProducts = System.currentTimeMillis();

        mongoMenuRepository.saveAll(mongoMenus);
        long saveToRepo = System.currentTimeMillis();

        List<MenuType> menus = menuServiceV2.getAll();
        long getMongoMenus = System.currentTimeMillis();

        System.out.println("deleteAll: " + (deleteAll - start) + "ms");
        System.out.println("getPSQLMenus: " + (getPSQLMenus - deleteAll) + "ms");
        System.out.println("convertProducts: " + (convertProducts - getPSQLMenus) + "ms");
        System.out.println("saveToRepo: " + (saveToRepo - convertProducts) + "ms");
        System.out.println("getMongoMenus: " + (getMongoMenus - saveToRepo) + "ms");
    }
}
