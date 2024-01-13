package com.springboot.dietapplication.model.psql.product;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Deprecated(since = "0.1.0", forRemoval = true)
@Entity
public class PsqlProductFoodProperties implements Serializable {

    @Serial
    private static final long serialVersionUID = 733139277397689916L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "category")
    private String category;

    @Column(name = "subcategory")
    private String subcategory;

    @Column(name = "energy_value")
    private int energyValue;

    @Column(name = "proteins")
    private float proteins;

    @Column(name = "fats")
    private float fats;

    @Column(name = "saturated_fatty_acids")
    private float saturatedFattyAcids;

    @Column(name = "mono_unsaturated_fatty_acids")
    private float monoUnsaturatedFattyAcids;

    @Column(name = "poly_unsaturated_fatty_acids")
    private float polyUnsaturatedFattyAcids;

    @Column(name = "cholesterol")
    private float cholesterol;

    @Column(name = "carbohydrates")
    private float carbohydrates;

    @Column(name = "sucrose")
    private float sucrose;

    @Column(name = "dietary_fibres")
    private float dietaryFibres;

    @Column(name = "sodium")
    private float sodium;

    @Column(name = "potassium")
    private float potassium;

    @Column(name = "calcium")
    private float calcium;

    @Column(name = "phosphorus")
    private float phosphorus;

    @Column(name = "magnesium")
    private float magnesium;

    @Column(name = "iron")
    private float iron;

    @Column(name = "selenium")
    private float selenium;

    @Column(name = "beta_carotene")
    private float betaCarotene;

    @Column(name = "vitamin_d")
    private float vitaminD;

    @Column(name = "vitamin_c")
    private float vitaminC;

    public PsqlProductFoodProperties() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public int getEnergyValue() {
        return energyValue;
    }

    public void setEnergyValue(int energyValue) {
        this.energyValue = energyValue;
    }

    public float getProteins() {
        return proteins;
    }

    public void setProteins(float proteins) {
        this.proteins = proteins;
    }

    public float getFats() {
        return fats;
    }

    public void setFats(float fats) {
        this.fats = fats;
    }

    public float getSaturatedFattyAcids() {
        return saturatedFattyAcids;
    }

    public void setSaturatedFattyAcids(float saturatedFattyAcids) {
        this.saturatedFattyAcids = saturatedFattyAcids;
    }

    public float getMonoUnsaturatedFattyAcids() {
        return monoUnsaturatedFattyAcids;
    }

    public void setMonoUnsaturatedFattyAcids(float monoUnsaturatedFattyAcids) {
        this.monoUnsaturatedFattyAcids = monoUnsaturatedFattyAcids;
    }

    public float getPolyUnsaturatedFattyAcids() {
        return polyUnsaturatedFattyAcids;
    }

    public void setPolyUnsaturatedFattyAcids(float polyUnsaturatedFattyAcids) {
        this.polyUnsaturatedFattyAcids = polyUnsaturatedFattyAcids;
    }

    public float getCholesterol() {
        return cholesterol;
    }

    public void setCholesterol(float cholesterol) {
        this.cholesterol = cholesterol;
    }

    public float getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(float carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public float getSucrose() {
        return sucrose;
    }

    public void setSucrose(float sucrose) {
        this.sucrose = sucrose;
    }

    public float getDietaryFibres() {
        return dietaryFibres;
    }

    public void setDietaryFibres(float dietaryFibres) {
        this.dietaryFibres = dietaryFibres;
    }

    public float getSodium() {
        return sodium;
    }

    public void setSodium(float sodium) {
        this.sodium = sodium;
    }

    public float getPotassium() {
        return potassium;
    }

    public void setPotassium(float potassium) {
        this.potassium = potassium;
    }

    public float getCalcium() {
        return calcium;
    }

    public void setCalcium(float calcium) {
        this.calcium = calcium;
    }

    public float getPhosphorus() {
        return phosphorus;
    }

    public void setPhosphorus(float phosphorus) {
        this.phosphorus = phosphorus;
    }

    public float getMagnesium() {
        return magnesium;
    }

    public void setMagnesium(float magnesium) {
        this.magnesium = magnesium;
    }

    public float getIron() {
        return iron;
    }

    public void setIron(float iron) {
        this.iron = iron;
    }

    public float getSelenium() {
        return selenium;
    }

    public void setSelenium(float selenium) {
        this.selenium = selenium;
    }

    public float getBetaCarotene() {
        return betaCarotene;
    }

    public void setBetaCarotene(float betaCarotene) {
        this.betaCarotene = betaCarotene;
    }

    public float getVitaminD() {
        return vitaminD;
    }

    public void setVitaminD(float vitaminD) {
        this.vitaminD = vitaminD;
    }

    public float getVitaminC() {
        return vitaminC;
    }

    public void setVitaminC(float vitaminC) {
        this.vitaminC = vitaminC;
    }
}
