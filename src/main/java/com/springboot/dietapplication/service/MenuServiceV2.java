package com.springboot.dietapplication.service;

import com.springboot.dietapplication.model.mongo.dish.MongoDishProduct;
import com.springboot.dietapplication.model.mongo.menu.*;
import com.springboot.dietapplication.model.mongo.patient.BriefPatient;
import com.springboot.dietapplication.model.mongo.patient.MongoPatient;
import com.springboot.dietapplication.model.mongo.product.MongoProduct;
import com.springboot.dietapplication.model.type.*;
import com.springboot.dietapplication.repository.mongo.MongoMenuRepository;
import com.springboot.dietapplication.repository.mongo.MongoPatientRepository;
import com.springboot.dietapplication.repository.mongo.MongoProductRepository;
import com.springboot.dietapplication.repository.mongo.MongoWeekMenuRepository;
import com.springboot.dietapplication.utils.DateFormatter;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MenuServiceV2 {

    @Autowired
    MongoMenuRepository mongoMenuRepository;
    @Autowired
    MongoWeekMenuRepository mongoWeekMenuRepository;
    @Autowired
    MongoPatientRepository mongoPatientRepository;
    @Autowired
    MongoProductRepository mongoProductRepository;

    public List<MenuType> getAll() {

        List<MongoMenu> mongoProductList = mongoMenuRepository.findAll();

        return mongoProductList
                .stream()
                .filter(p -> StringUtils.isEmpty(p.getDeletionDate()))
                .sorted( (p1, p2) -> {
                    if (p1.getCreationDate().equals(p2.getCreationDate())) {
                        return 0;
                    }
                    return p1.getCreationDate().equals(p2.getCreationDate()) ? 1 : -1;
                })
                .map(MenuType::new)
                .collect(Collectors.toList());

    }

    public MenuType getMenuById(String menuId) {
        MongoMenu menu = this.mongoMenuRepository
                .findById(menuId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Menu not found"));

        if (menu.getDeletionDate() != null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Menu was deleted");
        }

        MenuType menuType = new MenuType(menu);

        List<MongoWeekMenu> mongoWeekMenus = mongoWeekMenuRepository.findAllById(menu.getWeekMenus());
        menuType.setWeekMenuList(mongoWeekMenus.stream()
                .map(WeekMenuType::new)
                .collect(Collectors.toList()));

        return menuType;
    }

    public List<MenuType> getMenusByPatientId(String patientId) {
        List<MongoMenu> mongoProductList = mongoMenuRepository.findAll();

        return mongoProductList
                .stream()
                .filter(p -> StringUtils.isEmpty(p.getDeletionDate()) &&
                        p.getPatient().getId().equals(patientId))
                .sorted( (p1, p2) -> {
                    if (p1.getCreationDate().equals(p2.getCreationDate())) {
                        return 0;
                    }
                    return p1.getCreationDate().equals(p2.getCreationDate()) ? 1 : -1;
                })
                .map(MenuType::new)
                .collect(Collectors.toList());
    }

    public List<MenuProductType> getMenuProducts(String menuId) {

        List<MongoWeekMenu> weekMenus = this.mongoWeekMenuRepository.findByMenuId(menuId);

        List<MongoDishProduct> menuProducts = weekMenus.stream().flatMap(weekMenu -> weekMenu.getMeals().entrySet().stream()
                .flatMap(dayMeal -> dayMeal.getValue().stream())
                .flatMap(meal -> meal.getProducts().stream()))
                .collect(Collectors.toList());

        Set<String> productIds = menuProducts.stream().map(MongoDishProduct::getProductId).collect(Collectors.toSet());
        List<MongoProduct> products = this.mongoProductRepository.findAllById(productIds);

        return products.stream().map(product -> {

            float gramsOverall = menuProducts
                    .stream()
                    .filter( dp -> dp.getProductId().equals(product.getId()))
                    .map(MongoDishProduct::getGrams)
                    .reduce(0.0f, Float::sum);

            MenuProductType menuProductType = new MenuProductType();
            menuProductType.setId(product.getId());
            menuProductType.setName(product.getName());
            menuProductType.setCategory(product.getCategory());
            menuProductType.setGramsOverall(gramsOverall);

            return menuProductType;

        }).collect(Collectors.toList());
    }

    public ResponseEntity<MenuType> insert(NewMenuType newMenuType) {
        MongoMenu newMenu = new MongoMenu(newMenuType);

        Optional<MongoPatient> patient = mongoPatientRepository.findById(newMenuType.getPatientId());
        if (patient.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Patient not found");

        BriefPatient briefPatient = new BriefPatient(patient.get());
        newMenu.setPatient(briefPatient);

        LocalDate startDate;
        try {
            startDate = LocalDate.parse(newMenuType.getStartDate());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot parse startDate");
        }

        LocalDate endDate = startDate.plusWeeks(newMenuType.getWeekMenusCount()).minusDays(1);
        newMenu.setStartDate(startDate.toString());
        newMenu.setEndDate(endDate.toString());

        String currentDate =  DateFormatter.getInstance().getCurrentDate();
        newMenu.setCreationDate(currentDate);
        newMenu.setUpdateDate(currentDate);

        this.mongoMenuRepository.save(newMenu);

        BriefMenu briefNewMenu = new BriefMenu(newMenu);
        List<String> weekMenuIds = new ArrayList<>();
        for (int i = 0; i < newMenuType.getWeekMenusCount(); i++) {
            MongoWeekMenu mongoWeekMenu = new MongoWeekMenu();
            mongoWeekMenu.setMenu(briefNewMenu);

            Map<String, List<MongoMeal>> mongoDayMeals = new HashMap<>();

            for (int j = 0; j < 7; j++) {
                LocalDate localDate = startDate.plusDays(i* 7L + j);
                mongoDayMeals.put(localDate.toString(), new ArrayList<>());
            }
            mongoWeekMenu.setMeals(mongoDayMeals);

            mongoWeekMenu.setCreationDate(currentDate);
            mongoWeekMenu.setUpdateDate(currentDate);

            this.mongoWeekMenuRepository.save(mongoWeekMenu);
            weekMenuIds.add(mongoWeekMenu.getId());
        }

        newMenu.setWeekMenus(weekMenuIds);

        this.mongoMenuRepository.save(newMenu);

        return ResponseEntity.ok().body(new MenuType(newMenu));
    }

    public ResponseEntity<MenuType> update(NewMenuType updateMenuType) {
        if (updateMenuType.getId() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Menu id cannot be null");

        Optional<MongoMenu> currentMenu = this.mongoMenuRepository.findById(updateMenuType.getId());
        if (currentMenu.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Menu does not exist");

        MongoMenu updatedMenu = new MongoMenu(updateMenuType, currentMenu.get());

        String currentDate =  DateFormatter.getInstance().getCurrentDate();
        updatedMenu.setUpdateDate(currentDate);

        this.mongoMenuRepository.save(updatedMenu);

        // Update Menu References in WeekMenus items
        List<MongoWeekMenu> mongoWeekMenus = this.mongoWeekMenuRepository.findByMenuId(updatedMenu.getId());
        mongoWeekMenus.forEach(weekMenu -> weekMenu.setMenu(new BriefMenu(updatedMenu)));
        this.mongoWeekMenuRepository.saveAll(mongoWeekMenus);

        return ResponseEntity.ok().body(new MenuType(updatedMenu));
    }

    public ResponseEntity<Void> replaceProductInMenu(ProductReplaceType productReplaceType) {

        List<MongoWeekMenu> weekMenus = this.mongoWeekMenuRepository.findByMenuId(productReplaceType.getMenuId());

        MongoProduct newProduct = this.mongoProductRepository
                .findById(productReplaceType.getNewProductId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product does not exist"));

        weekMenus.forEach(weekMenu -> weekMenu.getMeals().entrySet().stream()
                .flatMap(dayMeal -> dayMeal.getValue().stream())
                .flatMap(meal -> meal.getProducts().stream())
                .filter(product -> product.getProductId().equals(productReplaceType.getOldProductId()))
                .forEach(product -> {
                    product.setProductId(newProduct.getId());
                    product.setName(newProduct.getName());
                    product.setFoodProperties(newProduct.getFoodProperties());
                    product.setAvailableAmountTypes(newProduct.getAmountTypes());
                }));

        this.mongoWeekMenuRepository.saveAll(weekMenus);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<MenuType> copy(NewMenuType copyMenuType) {
        if (copyMenuType.getId() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Menu id cannot be null");

        Optional<MongoMenu> originMenu = this.mongoMenuRepository.findById(copyMenuType.getId());
        if (originMenu.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Menu does not exist");

        Optional<MongoPatient> patient = mongoPatientRepository.findById(copyMenuType.getPatientId());
        if (patient.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Patient not found");

        MongoMenu copyMenu = new MongoMenu(copyMenuType);

        LocalDate startDate;
        long daysBetween;
        try {
            startDate = LocalDate.parse(originMenu.get().getStartDate());
            LocalDate newStartDate = LocalDate.parse(copyMenuType.getStartDate());
            daysBetween = ChronoUnit.DAYS.between(startDate, newStartDate);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot parse dates");
        }

        BriefPatient briefPatient = new BriefPatient(patient.get());
        copyMenu.setPatient(briefPatient);

        LocalDate newStartDate = startDate.plusDays(daysBetween);
        LocalDate newEndDate = newStartDate.plusWeeks(copyMenuType.getWeekMenusCount()).minusDays(1);
        copyMenu.setStartDate(newStartDate.toString());
        copyMenu.setEndDate(newEndDate.toString());

        String currentDate =  DateFormatter.getInstance().getCurrentDate();
        copyMenu.setCreationDate(currentDate);
        copyMenu.setUpdateDate(currentDate);

        List<MongoWeekMenu> originWeekMenus = this.mongoWeekMenuRepository.findByMenuId(copyMenuType.getId());

        this.mongoMenuRepository.save(copyMenu);

        BriefMenu briefNewMenu = new BriefMenu(copyMenu);
        float factor = Math.round((copyMenu.getEnergyLimit() / originMenu.get().getEnergyLimit()) * 100f) / 100f;

        List<String> weekMenuIds = new ArrayList<>();
        originWeekMenus.forEach( originWeekMenu -> {

            MongoWeekMenu mongoWeekMenu = new MongoWeekMenu();
            mongoWeekMenu.setMenu(briefNewMenu);
            mongoWeekMenu.setCreationDate(currentDate);
            mongoWeekMenu.setUpdateDate(currentDate);

            Map<String, List<MongoMeal>> mongoMeals = new HashMap<>();
            originWeekMenu.getMeals().forEach((dayString, meals) -> {
                LocalDate localDate = LocalDate.parse(dayString);

                meals.forEach(meal -> {
                    meal.setId(UUID.randomUUID().toString());
                    meal.setGrams(meal.getGrams() * factor);
                    meal.getProducts().forEach(product -> product.setGrams(product.getGrams() * factor));
                });

                mongoMeals.put(localDate.plusDays(daysBetween).toString(), meals);
            });

            mongoWeekMenu.setMeals(mongoMeals);

            this.mongoWeekMenuRepository.save(mongoWeekMenu);
            weekMenuIds.add(mongoWeekMenu.getId());
        });

        copyMenu.setWeekMenus(weekMenuIds);

        this.mongoMenuRepository.save(copyMenu);

        return ResponseEntity.ok().body(new MenuType(copyMenu));
    }

    public void delete(String id) {

        Optional<MongoMenu> mongoMenu = mongoMenuRepository.findById(id);
        if (mongoMenu.isEmpty()) return;

        // TODO: Allow delete menu only for authorized users
//        UserEntity user = userDetailsService.getCurrentUser();
//        if (!user.getUserType().equals(UserType.ADMIN.name) && !product.get().getUserId().equals(user.getId()))
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not authorized deleting product attempt");

        String currentDate =  DateFormatter.getInstance().getCurrentDate();
        mongoMenu.get().setDeletionDate(currentDate);

        mongoMenuRepository.save(mongoMenu.get());
    }

    public void updateMenuRecommendations(GenerateMenuType generateMenuType) {
        Optional<MongoMenu> mongoMenu = mongoMenuRepository.findById(generateMenuType.getMenuId());
        if (mongoMenu.isEmpty()) return;

        mongoMenu.get().setRecommendations(generateMenuType.getRecommendations());

        mongoMenuRepository.save(mongoMenu.get());
    }


    public void updateMenuPatients(MongoPatient updatedPatient) {
        List<MongoMenu> menus = mongoMenuRepository.findByPatientId(updatedPatient.getId());
        menus.forEach(menu -> menu.setPatient(new BriefPatient(updatedPatient)));
        mongoMenuRepository.saveAll(menus);
    }
}

