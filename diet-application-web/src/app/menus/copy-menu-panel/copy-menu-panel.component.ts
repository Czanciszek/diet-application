import { Component, OnInit } from '@angular/core';

import {MatDialog, MatDialogRef} from "@angular/material/dialog";
import {PatientService} from "../../service/patient.service";
import {MenuService} from "../../service/menu.service";
import {NotificationService} from "../../service/notification.service";

import {Patient} from "../../model/patient";

@Component({
  selector: 'app-copy-menu-panel',
  templateUrl: './copy-menu-panel.component.html',
  styleUrls: ['./copy-menu-panel.component.css']
})
export class CopyMenuPanelComponent implements OnInit {

  constructor(
    private patientService: PatientService,
    private menuService: MenuService,
    private notificationService: NotificationService,
    public dialogRef: MatDialogRef<CopyMenuPanelComponent>
  ) { }

    public menuItem: any;
    copyItem: any;
    patientList: any;

    ngOnInit(): void {
      this.copyItem = Object.assign({}, this.menuItem);
      this.getPatients();
    }

    onClose() {
      this.dialogRef.close();
    }

    getPatients() {
      this.patientService.getPatients().subscribe(
        (data: Patient[] ) => {
          this.patientList = [...data];
        });
    }

  onPatientChange(patientId) {
    this.copyItem.patientId = patientId;
  }

  limitsChanged(newLimitValue) {
    this.copyItem.energyLimit = newLimitValue;
    let factor = Number(newLimitValue / this.menuItem.energyLimit);

    this.copyItem.proteinsLimit = Number(factor * this.menuItem.proteinsLimit).toFixed(2);
    this.copyItem.fatsLimit = Number(factor * this.menuItem.fatsLimit).toFixed(2);
    this.copyItem.carbohydratesLimit = Number(factor * this.menuItem.carbohydratesLimit).toFixed(2);
  }

  onCopyButtonClicked() {
    if (!this.areFieldsValid()) {
      this.notificationService.warn(":: Niepoprawne dane! ::");
      return;
    }
    this.menuService.copyMenu(this.copyItem).subscribe( result => {
      this.notificationService.success(":: Skopiowano pomyślnie! ::");
    }, error => {
     this.notificationService.warn(":: Wystąpił problem z kopiowaniem! ::");
    }, () => {
      this.onClose();
    });
  }

  areFieldsValid() {
    return this.copyItem.patientId != null &&
     this.copyItem.energyLimit > 0 &&
     this.copyItem.id != null;
  }

}
