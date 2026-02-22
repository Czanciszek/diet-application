package com.springboot.dietapplication.model.type;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Document("mainSchemes")
public class MongoMealMainScheme implements Serializable {

    @Serial
    private static final long serialVersionUID = -4432284006187622050L;

    @Id
    private String id;

    private String patientId;
    private Integer order;
    private FoodType foodType;
    private Float schemeCalories;

    private MongoSchemeBaseComponent proteinsBaseComponent;
    private MongoSchemeBaseComponent fatsBaseComponent;
    private MongoSchemeBaseComponent carbohydratesBaseComponent;
    private MongoSchemeBaseComponent fibresBaseComponent;

    public MongoMealMainScheme(MealMainSchemeCreateRequest createRequest) {
        this.patientId = createRequest.getPatientId();
        this.order = createRequest.getOrder();
        this.foodType = createRequest.getFoodType();

        if (createRequest.getProteinsBase() != null) {
            this.proteinsBaseComponent = new MongoSchemeBaseComponent(createRequest.getProteinsBase());
        }

        if (createRequest.getFatsBase() != null) {
            this.fatsBaseComponent = new MongoSchemeBaseComponent(createRequest.getFatsBase());
        }

        if (createRequest.getCarbohydratesBase() != null) {
            this.carbohydratesBaseComponent = new MongoSchemeBaseComponent(createRequest.getCarbohydratesBase());
        }

        if (createRequest.getFibresBase() != null) {
            this.fibresBaseComponent = new MongoSchemeBaseComponent(createRequest.getFibresBase());
        }
    }
}
