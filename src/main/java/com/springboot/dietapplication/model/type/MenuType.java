package com.springboot.dietapplication.model.type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.springboot.dietapplication.model.mongo.menu.MongoMenu;
import com.springboot.dietapplication.model.mongo.patient.BriefPatient;
import com.springboot.dietapplication.model.psql.menu.PsqlMenu;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class MenuType implements Serializable {

    @Serial
    private static final long serialVersionUID = -4825052408639527082L;

    private String id;

    @JsonIgnore
    private String userId;

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private BriefPatient patient;

    private int weekMenusCount;

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private List<WeekMenuType> weekMenuList; // Lista tygodniowych jadłospisów

    private List<FoodType> foodTypes; // Rodzaje posiłków do menu

    private String startDate; // Najwcześniejsza data z listy DayMeals

    private String endDate; // Najpóźniejsza data z listy DayMeals

    private String recommendations;

    private float energyLimit;

    private float proteinsLimit;

    private float fatsLimit;

    private float carbohydratesLimit;

    public MenuType() {}

    public MenuType(PsqlMenu menu) {
        this.id = String.valueOf(menu.getId());
        this.startDate = menu.getStartDate();
        this.endDate = menu.getEndDate();
        this.recommendations = menu.getRecommendations();
        this.energyLimit = menu.getEnergyLimit();
        this.proteinsLimit = menu.getProteinsLimit();
        this.fatsLimit = menu.getFatsLimit();
        this.carbohydratesLimit = menu.getCarbohydratesLimit();
    }

    public MenuType(MongoMenu mongoMenu) {
        this.id = mongoMenu.getId();
        this.patient = mongoMenu.getPatient();
        this.startDate = mongoMenu.getStartDate();
        this.endDate = mongoMenu.getEndDate();
        this.recommendations = mongoMenu.getRecommendations();
        this.foodTypes = mongoMenu.getFoodTypes();
        this.energyLimit = mongoMenu.getEnergyLimit();
        this.proteinsLimit = mongoMenu.getProteinsLimit();
        this.fatsLimit = mongoMenu.getFatsLimit();
        this.carbohydratesLimit = mongoMenu.getCarbohydratesLimit();
        this.weekMenusCount = mongoMenu.getWeekMenus().size();
    }

}
