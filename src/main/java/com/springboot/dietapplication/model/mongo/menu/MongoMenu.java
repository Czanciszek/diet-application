package com.springboot.dietapplication.model.mongo.menu;

import com.springboot.dietapplication.model.mongo.patient.BriefPatient;
import com.springboot.dietapplication.model.type.FoodType;
import com.springboot.dietapplication.model.type.NewMenuType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
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
