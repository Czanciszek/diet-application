package com.springboot.dietapplication.controller;

import com.springboot.dietapplication.model.type.*;
import com.springboot.dietapplication.service.MenuServiceV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/v2/menus")
public class MenuControllerV2 {

    @Autowired
    MenuServiceV2 menuService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<MenuType> menuList = this.menuService.getAll();
        return ResponseEntity.ok(menuList);
    }

    @GetMapping(path = "/{menuId}")
    public ResponseEntity<?> getMenuById(@PathVariable("menuId") String menuId) {
        MenuType menu = this.menuService.getMenuById(menuId);
        return ResponseEntity.ok(menu);
    }

    @GetMapping(path = "/patient/{patientId}")
    public ResponseEntity<?> getMenusByPatientId(@PathVariable("patientId") String patientId) {
        List<MenuType> menuList = this.menuService.getMenusByPatientId(patientId);
        return ResponseEntity.ok(menuList);
    }

    @GetMapping(path = "/{menuId}/products")
    public ResponseEntity<?> menuProducts(@PathVariable("menuId") String menuId) {
        List<MenuProductType> menuProducts = this.menuService.getMenuProducts(menuId);
        return ResponseEntity.ok(menuProducts);
    }

    @PostMapping(produces = "application/json")
    ResponseEntity<MenuType> insert(@RequestBody NewMenuType newMenuType) {
        return this.menuService.insert(newMenuType);
    }

    @PostMapping(path = "/copy", produces = "application/json")
    ResponseEntity<MenuType> copy(@RequestBody NewMenuType copyMenuType) {
        return this.menuService.copy(copyMenuType);
    }

    @PostMapping(path = "/product-replace", produces = "application/json")
    ResponseEntity<Void> replaceProductInMenu(@RequestBody ProductReplaceType productReplaceType) {
        return this.menuService.replaceProductInMenu(productReplaceType);
    }

    @PutMapping(produces = "application/json")
    ResponseEntity<MenuType> update(@RequestBody NewMenuType updateMenuType) {
        return this.menuService.update(updateMenuType);
    }
    
    @DeleteMapping(path = "/{id}")
    ResponseEntity<?> deleteMenu(@PathVariable String id) {
        this.menuService.delete(id);
        return ResponseEntity.ok().build();
    }

}
