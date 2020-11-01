package com.springboot.dietapplication.controller;

import com.springboot.dietapplication.model.menu.Menu;
import com.springboot.dietapplication.repository.MenuRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/menus")
public class MenuController {
    private MenuRepository menuRepository;

    public MenuController(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @GetMapping
    public List<Menu> getAll() {
        return this.menuRepository.findAll();
    }

    @PostMapping(produces = "application/json")
    ResponseEntity<Menu> insertMenu(@RequestBody Menu menu) throws NoSuchFieldException {
        menuRepository.save(menu);
        return ResponseEntity.ok().body(null);
    }

    @PutMapping(path = "/{menuId}", produces = "application/json")
    ResponseEntity<Menu> updateMenu(@RequestBody Menu menu) {
        menuRepository.save(menu);
        return ResponseEntity.ok().body(menu);
    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity<Menu> deleteMenu(@PathVariable String id) {
        menuRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
