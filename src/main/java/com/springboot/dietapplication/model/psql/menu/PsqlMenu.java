package com.springboot.dietapplication.model.psql.menu;

import com.springboot.dietapplication.model.type.MenuSendingType;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "menus")
public class PsqlMenu implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "patient_id")
    private long patientId;

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
        this.patientId = Long.parseLong(menuSendingType.getPatientId());
        this.energyLimit = menuSendingType.getEnergyLimit();
        this.proteinsLimit = menuSendingType.getProteinsLimit();
        this.fatsLimit = menuSendingType.getFatsLimit();
        this.carbohydratesLimit = menuSendingType.getCarbohydratesLimit();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
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