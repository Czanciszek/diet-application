package com.springboot.dietapplication.model.mongo.menu;

import com.springboot.dietapplication.model.mongo.patient.BriefPatient;
import com.springboot.dietapplication.model.type.FoodType;
import com.springboot.dietapplication.model.type.MenuType;
import com.springboot.dietapplication.model.type.NewMenuType;
import com.springboot.dietapplication.model.type.WeekMenuType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Document("menus")
public class MongoMenu {

    @Id
    private String id;

    private BriefPatient patient;

    private String startDate;
    private String endDate;

    private Float energyLimit;
    private Float fatsLimit;
    private Float proteinsLimit;
    private Float carbohydratesLimit;

    private String recommendations;
    private List<FoodType> foodTypes;

    private String creationDate;
    private String updateDate;
    private String deletionDate;

    private List<String> weekMenus;

    public MongoMenu() {}

    public MongoMenu(MenuType menuType) {
        this.id = menuType.getId();
        this.patient = menuType.getPatient();

        this.startDate = menuType.getStartDate();
        this.endDate = menuType.getEndDate();

        this.energyLimit = menuType.getEnergyLimit();
        this.fatsLimit = menuType.getFatsLimit();
        this.proteinsLimit = menuType.getProteinsLimit();
        this.carbohydratesLimit = menuType.getCarbohydratesLimit();

        this.recommendations = menuType.getRecommendations();
        this.foodTypes = menuType.getFoodTypes();

        if (menuType.getWeekMenuList() != null) {
            this.weekMenus = menuType.getWeekMenuList()
                    .stream()
                    .map(WeekMenuType::getId)
                    .collect(Collectors.toList());
        } else if (menuType.getWeekMealList() != null) {
            this.weekMenus = menuType.getWeekMealList()
                    .stream()
                    .map(String::valueOf)
                    .collect(Collectors.toList());
        } else {
            this.weekMenus = new ArrayList<>();
        }
    }

    // Insert new menu
    public MongoMenu(NewMenuType newMenuType) {
        this.energyLimit = newMenuType.getEnergyLimit();
        this.proteinsLimit = newMenuType.getProteinsLimit();
        this.fatsLimit = newMenuType.getFatsLimit();
        this.carbohydratesLimit = newMenuType.getCarbohydratesLimit();

        this.recommendations = newMenuType.getRecommendations();
        this.foodTypes = newMenuType.getFoodTypes();
    }

    // Update existing Menu
    public MongoMenu(NewMenuType newMenuType, MongoMenu currentMenu) {
        this.energyLimit = newMenuType.getEnergyLimit();
        this.proteinsLimit = newMenuType.getProteinsLimit();
        this.fatsLimit = newMenuType.getFatsLimit();
        this.carbohydratesLimit = newMenuType.getCarbohydratesLimit();
        this.recommendations = newMenuType.getRecommendations();
        this.foodTypes = newMenuType.getFoodTypes();

        this.id = currentMenu.getId();
        this.patient = currentMenu.getPatient();
        this.startDate = currentMenu.getStartDate();
        this.endDate = currentMenu.getEndDate();
        this.weekMenus = currentMenu.getWeekMenus();
        this.creationDate = currentMenu.getCreationDate();
    }
}
