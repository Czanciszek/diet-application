import { Component, OnInit } from '@angular/core';
import {NotificationService} from "../../service/notification.service";
import {MatDialogRef} from "@angular/material/dialog";
import {MenuService} from "../../service/menu.service";
import {MeasurementService} from "../../service/measurement.service";
import {Measurement} from "../../model/measurement";
import {GlobalVariable} from "../../global";

@Component({
  selector: 'app-menu-add',
  templateUrl: './menu-add.component.html',
  styleUrls: ['./menu-add.component.css']
})
export class MenuAddComponent implements OnInit {

  constructor(
    private service: MenuService,
    private measurementService: MeasurementService,
    private notificationService: NotificationService,
    public dialogRef: MatDialogRef<MenuAddComponent>
  ) { }

  mealTypes = [
    { id: "BREAKFEST", value: "Śniadanie" },
    { id: "BRUNCH", value: "II śniadanie" },
    { id: "DINNER", value: "Obiad" },
    { id: "TEA", value: "Podwieczorek"},
    { id: "SUPPER", value: "Kolacja" },
    { id: "PRE_WORKOUT", value: "Przedtreningówka" },
    { id: "POST_WORKOUT", value: "Potreningówka"},
  ];

  measurementDates = [];

  ngOnInit(): void {
    if (GlobalVariable.DATABASE_SERVICE.toString().includes("mongo")) {
      this.getMeasurementList("5ff05f1fde578b7aa1774775");
    } else {
      this.getMeasurementList("83709");
    }
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
          const dateFormat = new Date(measurementList[index].measurementDate);
          const dateString = dateFormat.getDate() + "/"
            + (dateFormat.getMonth() + 1) + "/" + dateFormat.getFullYear();
          this.measurementDates.push({ id: measurementList[index].id, value: dateString });
        }
      }
    }
  }

  getMeasurementDates() {
    return this.measurementDates;
  }

  onSubmit() {
    if (this.service.form.valid) {
      if (GlobalVariable.DATABASE_SERVICE.toString().includes("mongo")) {
        this.service.form.get('patientId').setValue("5ff05f1fde578b7aa1774775");
      } else {
        this.service.form.get('patientId').setValue(83709);
      }

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

}
