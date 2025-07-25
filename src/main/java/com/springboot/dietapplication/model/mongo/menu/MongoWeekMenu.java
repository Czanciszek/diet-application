package com.springboot.dietapplication.model.mongo.menu;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@Document("weeklyMenus")
public class MongoWeekMenu {

    @Id
    private String id;

    private BriefMenu menu;

    private Map<String, List<MongoMeal>> meals;

    private String creationDate;
    private String updateDate;
    private String deletionDate;

}
