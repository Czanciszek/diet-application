package com.springboot.dietapplication.model.psql.menu;

import com.springboot.dietapplication.model.type.MenuSendingType;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "menus")
public class PsqlMenu implements Serializable {

    private static final long serialVersionUID = -5365981026625565450L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "patient_id")
    private Long patientId;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "end_date")
    private String endDate;

    @Column(name = "energy_limit")
    private float energyLimit;

    @Column(name = "proteins_limit")
    private float proteinsLimit;

    @Column(name = "fats_limit")
    private float fatsLimit;

    @Column(name = "carbohydrates_limit")
    private float carbohydratesLimit;

    public PsqlMenu() {

    }

    public PsqlMenu(MenuSendingType menuSendingType) {
        this.startDate = menuSendingType.getStartDate();
        this.patientId = menuSendingType.getPatientId();
        this.energyLimit = menuSendingType.getEnergyLimit();
        this.proteinsLimit = menuSendingType.getProteinsLimit();
        this.fatsLimit = menuSendingType.getFatsLimit();
        this.carbohydratesLimit = menuSendingType.getCarbohydratesLimit();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
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

    public float getEnergyLimit() {
        return energyLimit;
    }

    public void setEnergyLimit(float energyLimit) {
        this.energyLimit = energyLimit;
    }

    public float getProteinsLimit() {
        return proteinsLimit;
    }

    public void setProteinsLimit(float proteinsLimit) {
        this.proteinsLimit = proteinsLimit;
    }

    public float getFatsLimit() {
        return fatsLimit;
    }

    public void setFatsLimit(float fatsLimit) {
        this.fatsLimit = fatsLimit;
    }

    public float getCarbohydratesLimit() {
        return carbohydratesLimit;
    }

    public void setCarbohydratesLimit(float carbohydratesLimit) {
        this.carbohydratesLimit = carbohydratesLimit;
    }
}