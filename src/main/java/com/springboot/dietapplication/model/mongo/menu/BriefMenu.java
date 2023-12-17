package com.springboot.dietapplication.model.mongo.menu;

import com.springboot.dietapplication.model.type.FoodType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BriefMenu {

    private String id;
    private List<FoodType> foodTypes;
    private String startDate;
    private String endDate;

    private Float energyLimit;
    private Float fatsLimit;
    private Float proteinsLimit;
    private Float carbohydratesLimit;

    public BriefMenu() {}

    public BriefMenu(MongoMenu mongoMenu) {
        this.id = mongoMenu.getId();
        this.foodTypes = mongoMenu.getFoodTypes();
        this.startDate = mongoMenu.getStartDate();
        this.endDate = mongoMenu.getEndDate();
        this.energyLimit = mongoMenu.getEnergyLimit();
        this.fatsLimit = mongoMenu.getFatsLimit();
        this.proteinsLimit = mongoMenu.getProteinsLimit();
        this.carbohydratesLimit = mongoMenu.getCarbohydratesLimit();
    }
}
