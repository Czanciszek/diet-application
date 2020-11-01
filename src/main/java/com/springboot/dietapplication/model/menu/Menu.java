package com.springboot.dietapplication.model.menu;

import com.springboot.dietapplication.model.base.BaseDoc;
import com.springboot.dietapplication.model.base.DocRef;
import com.springboot.dietapplication.model.patient.Patient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Menus")
public class Menu extends BaseDoc {

    private DocRef<Patient> patientDocRef; // Dane pacjenta

    private String menuStartDate; // Data rozpoczęcia

    private List<DayMeal> dayMealList; // Zbiór dziennych posiłków

    private List<MealType> mealTypes; // Rodzaje posiłków do menuy

    //private Map<String, Float> limits; // Limity kaloryczności

    protected Menu() {

    }

    public Menu(DocRef<Patient> patientDocRef, String menuStartDate,
                List<DayMeal> dayMealList, List<MealType> mealTypes) {
        this.patientDocRef = patientDocRef;
        this.menuStartDate = menuStartDate;
        this.dayMealList = dayMealList;
        this.mealTypes = mealTypes;
    }

    public DocRef<Patient> getPatientDocRef() {
        return patientDocRef;
    }

    public void setPatientDocRef(DocRef<Patient> patientDocRef) {
        this.patientDocRef = patientDocRef;
    }

    public String getMenuStartDate() {
        return menuStartDate;
    }

    public void setMenuStartDate(String menuStartDate) {
        this.menuStartDate = menuStartDate;
    }

    public List<DayMeal> getDayMealList() {
        return dayMealList;
    }

    public void setDayMealList(List<DayMeal> dayMealList) {
        this.dayMealList = dayMealList;
    }

    public List<MealType> getMealTypes() {
        return mealTypes;
    }

    public void setMealTypes(List<MealType> mealTypes) {
        this.mealTypes = mealTypes;
    }
}
