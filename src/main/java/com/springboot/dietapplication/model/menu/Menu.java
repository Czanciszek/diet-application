package com.springboot.dietapplication.model.menu;

import com.springboot.dietapplication.model.base.BaseDoc;
import com.springboot.dietapplication.model.base.DocRef;
import com.springboot.dietapplication.model.patient.Measurement;
import com.springboot.dietapplication.model.patient.Patient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Menus")
public class Menu extends BaseDoc {

    private DocRef<Patient> patientDocRef; // Ref - Dane pacjenta

    private DocRef<Measurement> measurementDocRef; // Ref - Dane pomiarowe

    private List<String> weekMealList; // Lista odnośników do tygodniowych jadłospisów

    private List<MealType> mealTypes; // Rodzaje posiłków do menu

    private String startDate; // Najwcześniejsza data z listy DayMeals

    private String endDate; // Najpóźniejsza data z listy DayMeals

    public Menu() {

    }

    public Menu(Menu menu) {
        this.patientDocRef = menu.patientDocRef;
        this.measurementDocRef = menu.measurementDocRef;
        this.weekMealList = menu.weekMealList;
        this.mealTypes = menu.mealTypes;
        this.startDate = menu.startDate;
        this.endDate = menu.endDate;
    }

    public Menu(DocRef<Patient> patientDocRef, DocRef<Measurement> measurementDocRef, List<String> weekMealList, List<MealType> mealTypes,
                String startDate, String endDate) {
        this.patientDocRef = patientDocRef;
        this.measurementDocRef = measurementDocRef;
        this.weekMealList = weekMealList;
        this.mealTypes = mealTypes;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public DocRef<Patient> getPatientDocRef() {
        return patientDocRef;
    }

    public void setPatientDocRef(DocRef<Patient> patientDocRef) {
        this.patientDocRef = patientDocRef;
    }

    public DocRef<Measurement> getMeasurementDocRef() {
        return measurementDocRef;
    }

    public void setMeasurementDocRef(DocRef<Measurement> measurementDocRef) {
        this.measurementDocRef = measurementDocRef;
    }

    public List<String> getWeekMealList() {
        return weekMealList;
    }

    public void setWeekMealList(List<String> weekMealList) {
        this.weekMealList = weekMealList;
    }

    public List<MealType> getMealTypes() {
        return mealTypes;
    }

    public void setMealTypes(List<MealType> mealTypes) {
        this.mealTypes = mealTypes;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}