package com.springboot.dietapplication.controller;

import com.springboot.dietapplication.model.type.MenuSendingType;
import com.springboot.dietapplication.model.type.MenuType;
import com.springboot.dietapplication.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/menus")
public class MenuController {

    @Autowired
    MenuService menuService;

    @GetMapping
    public List<MenuType> getAll() {
        return this.menuService.getAll();
    }

    @GetMapping(path = "/{menuId}")
    public MenuType getMenuById(@PathVariable("menuId") Long menuId) {
        return this.menuService.getMenuById(menuId);
    }

    @GetMapping(path = "/patient/{patientId}")
    public List<MenuType> getMenusByPatientId(@PathVariable("patientId") Long patientId) {
        return this.menuService.getMenusByPatientId(patientId);
    }

    @PostMapping(produces = "application/json")
    ResponseEntity<MenuType> insertMenu(@RequestBody MenuSendingType menuSendingType) {
        return this.menuService.insert(menuSendingType);
    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity<MenuType> deleteMenu(@PathVariable Long id) {
        return this.menuService.delete(id);
    }

}
