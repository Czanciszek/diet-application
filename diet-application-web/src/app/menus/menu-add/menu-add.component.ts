import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import {NotificationService} from "../../service/notification.service";
import {MatDialogRef} from "@angular/material/dialog";
import {MenuService} from "../../service/menu.service";
import {MeasurementService} from "../../service/measurement.service";
import {Measurement} from "../../model/measurement";
import {PatientService} from "../../service/patient.service";
import {FOOD_TYPES} from "../../model/helpers/foodTypes";

@Component({
  selector: 'app-menu-add',
  templateUrl: './menu-add.component.html',
  styleUrls: ['./menu-add.component.css']
})
export class MenuAddComponent implements OnInit {

  constructor(
    private service: MenuService,
    private measurementService: MeasurementService,
    private patientService: PatientService,
    private notificationService: NotificationService,
    public dialogRef: MatDialogRef<MenuAddComponent>
  ) { }

  customLimits = false;

  foodTypes = FOOD_TYPES;

  measurementDates = [];

  currentPatientId = "";

  ngOnInit(): void {
    this.currentPatientId = this.patientService.form.get("id").value;
    this.getMeasurementList(this.currentPatientId);
  }

  onClear() {
    this.service.form.reset();
    this.service.initializeFormGroup();
  }

  onClose() {
    this.onClear();
    this.dialogRef.close();
  }

  onlyMonday = (d: Date): boolean => {
    const day = d.getDay();
    return day == 1;
  }

  getMeasurementList(patientId) {
    this.measurementService.getMeasurementsByPatientId(patientId)
      .subscribe(
        (data: Measurement[]) => {
          const measurementList = { ...data};
          this.prepareMeasurementDates(measurementList);
        }
      );
  }

  prepareMeasurementDates(measurementList) {
    if (measurementList != null) {
      for (let index in measurementList) {
        if (measurementList[index].measurementDate != null) {
          const patientWeight = measurementList[index].bodyWeight;
          const dateFormat = new Date(measurementList[index].measurementDate);
          const dateString = dateFormat.getDate() + "/"
            + (dateFormat.getMonth() + 1) + "/" + dateFormat.getFullYear();
          this.measurementDates.push({ id: measurementList[index].id, date: dateString, weight: patientWeight });
        }
      }
    }
  }

  getMeasurementDates() {
    return this.measurementDates;
  }

  onSubmit() {
    if (this.service.form.valid) {
      let dateWeight = this.service.form.get("measurementDate").value.split("&");
      let date = dateWeight[0];
      let weight = dateWeight[1];

      this.service.form.get("patientId").setValue(this.currentPatientId);
      this.service.form.get("measurementDate").setValue(date);
      this.service.form.get("patientWeight").setValue(weight);

      if (!this.service.form.get('id').value) {
        this.service.insertMenu(this.service.form.value).subscribe();
        this.notificationService.success(":: Menu created successfully! ::");
      } else {
        //this.service.updateMenu(this.service.form.value).subscribe();
        //this.notificationService.success(":: Menu updated successfully! ::");
      }
      this.onClose();
    }
  }

  @ViewChild('limits') limits: ElementRef;

  @ViewChild('proteinsPercentage') proteinsPercentage: ElementRef;
  @ViewChild('fatsPercentage') fatsPercentage: ElementRef;
  @ViewChild('carbohydratesPercentage') carbohydratesPercentage: ElementRef;

  @ViewChild('proteinsValue') proteinsValue: ElementRef;
  @ViewChild('fatsValue') fatsValue: ElementRef;
  @ViewChild('carbohydratesValue') carbohydratesValue: ElementRef;

  customLimitsOptionChanged(event) {
    this.customLimits = event.source.checked;
  }

  limitsChanged(newLimitValue, initial) {

  }

  fatsPercentageChanged(newValue: number, initial: boolean) {
    newValue = this.checkPercentage(newValue);
    this.fatsPercentage.nativeElement.value = newValue;

    let limit = Number(this.limits.nativeElement.value);
    if (initial && limit > 0) {
      let grams =  Number((limit * newValue / 100 / 9).toFixed(1));
      this.fatsValueChanged(grams, false);
    }
  }

  fatsValueChanged(newValue: number, initial: boolean) {
    newValue = this.checkValue(newValue);
    this.fatsValue.nativeElement.value = newValue;

    let limit = Number(this.limits.nativeElement.value);
    if (initial && limit > 0) {
      let percentage = Number((newValue / limit * 100 * 9).toFixed(2));
      this.fatsPercentageChanged(percentage, false);
    } else {
      // Check if both other fields are filled
    }
  }

  proteinsValueChanged(newValue, initial) {
    console.log("proteinsValueChanged " + newValue);
  }

  proteinsPercentageChanged(newValue, initial) {
    console.log("proteinsPercentageChanged " + newValue);
  }

  carbohydratesValueChanged(newValue, initial) {
    console.log("carbohydratesValueChanged " + newValue);
  }

  carbohydratesPercentageChanged(newValue, initial) {
    console.log("carbohydratesPercentageChanged " + newValue);
  }

  checkPercentage(percentage: number) {
    if (percentage > 100) {
      percentage = 100;
    } else if (percentage < 0) {
      percentage = 0;
    }
    return percentage;
  }

  checkValue(value: number) {
    let limit = Number(this.limits.nativeElement.value);

    if (value > limit) {
      value = limit;
    } else if (value < 0) {
      value = 0;
    }

    return value;
  }
}
