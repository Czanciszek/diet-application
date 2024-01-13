package com.springboot.dietapplication.controller;

import com.springboot.dietapplication.model.psql.product.PsqlShoppingProduct;
import com.springboot.dietapplication.model.type.NewMenuType;
import com.springboot.dietapplication.model.type.MenuType;
import com.springboot.dietapplication.model.type.ProductReplaceType;
import com.springboot.dietapplication.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Deprecated(since = "0.1.0", forRemoval = true)
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

    @GetMapping(path = "/{menuId}/products")
    public List<PsqlShoppingProduct> menuShoppingProducts(@PathVariable("menuId") long menuId) {
        return this.menuService.getShoppingProductsForMenu(menuId);
    }

    @PostMapping(produces = "application/json")
    ResponseEntity<MenuType> insert(@RequestBody NewMenuType newMenuType) {
        return this.menuService.insert(newMenuType);
    }

    @PostMapping(path = "/product-replace/{menuId}", produces = "application/json")
    ResponseEntity<Void> replaceProductInMenu(
            @PathVariable("menuId") Long menuId,
            @RequestBody ProductReplaceType productReplaceType) {
        return this.menuService.replaceProductInMenu(menuId, productReplaceType);
    }

    @PutMapping(path = "/{menuId}", produces = "application/json")
    ResponseEntity<MenuType> update(@PathVariable("menuId") Long menuId,
                                    @RequestBody NewMenuType newMenuType) {
        return this.menuService.update(newMenuType);
    }

    @PostMapping(path = "/copy", produces = "application/json")
    ResponseEntity<MenuType> copy(@RequestBody NewMenuType newMenuType) {
        return this.menuService.copy(newMenuType);
    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity<MenuType> deleteMenu(@PathVariable Long id) {
        return this.menuService.delete(id);
    }

}
