package com.springboot.dietapplication.model.mongo;

import com.springboot.dietapplication.model.type.DishType;
import com.springboot.dietapplication.model.type.FoodType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Document("dishes")
public class MongoDish {

    @Id
    private String id;

    private String userId;

    private String name;
    private String recipe;
    private Float portions;
    private List<FoodType> foodTypes;
    private List<MongoDishProduct> products;

    private String creationDate;
    private String updateDate;
    private String deletionDate;

    public MongoDish() {}

    public MongoDish(DishType dishType) {
        this.id = dishType.getId();
        this.userId = String.valueOf(dishType.getUserId());
        this.name = dishType.getName();
        this.recipe = dishType.getRecipe();
        this.portions = dishType.getPortions();
        this.foodTypes = List.of(dishType.getFoodType());

        this.products = dishType.getProducts()
                .stream()
                .map(MongoDishProduct::new)
                .collect(Collectors.toList());
    }

}
