package com.springboot.dietapplication.model.type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.springboot.dietapplication.model.mongo.menu.MongoMenu;
import com.springboot.dietapplication.model.mongo.patient.BriefPatient;
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

    @JsonIgnore
    private List<Long> weekMealList; // Lista odnośników do tygodniowych jadłospisów

    @JsonIgnore
    private List<DayMealType> dayMealTypeList; //Lista odnośników do dni w jadłospisie

    private List<FoodType> foodTypes; // Rodzaje posiłków do menu

    private String startDate; // Najwcześniejsza data z listy DayMeals

    private String endDate; // Najpóźniejsza data z listy DayMeals

    private String recommendations;

    private float energyLimit;

    private float proteinsLimit;

    private float fatsLimit;

    private float carbohydratesLimit;

    public MenuType() {}

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
