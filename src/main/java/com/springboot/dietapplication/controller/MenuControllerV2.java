package com.springboot.dietapplication.controller;

import com.springboot.dietapplication.model.type.*;
import com.springboot.dietapplication.service.MenuServiceV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/v2/menus")
public class MenuControllerV2 {

    @Autowired
    MenuServiceV2 menuService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            List<MenuType> menuList = this.menuService.getAll();
            return ResponseEntity.ok(menuList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(path = "/{menuId}")
    public ResponseEntity<?> getMenuById(@PathVariable("menuId") String menuId) {
        try {
            MenuType menu = this.menuService.getMenuById(menuId);
            return ResponseEntity.ok(menu);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<HttpStatus>(e.getStatusCode());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(menuId);
        }
    }

    @GetMapping(path = "/patient/{patientId}")
    public ResponseEntity<?> getMenusByPatientId(@PathVariable("patientId") String patientId) {
        try {
            List<MenuType> menuList = this.menuService.getMenusByPatientId(patientId);
            return ResponseEntity.ok(menuList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(path = "/{menuId}/products")
    public ResponseEntity<?> menuProducts(@PathVariable("menuId") String menuId) {
        try {
            List<MenuProductType> menuProducts = this.menuService.getMenuProducts(menuId);
            return ResponseEntity.ok(menuProducts);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
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
        try {
            this.menuService.delete(id);
            return ResponseEntity.ok().build();
        } catch (ResponseStatusException e) {
            return new ResponseEntity<HttpStatus>(e.getStatusCode());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(id);
        }
    }

}
