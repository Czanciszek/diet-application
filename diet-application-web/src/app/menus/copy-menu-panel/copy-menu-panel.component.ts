import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from "@angular/material/dialog";

import { PatientService } from "../../service/patient.service";
import { MenuService } from "../../service/menu.service";
import { NotificationService } from "../../service/notification.service";

import { Menu } from "../../model/menu";
import { Patient } from "../../model/patient";

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

  menuServiceForm = this.menuService.form;
  currentMenuItem: Menu;

  patientList: any;

  ngOnInit(): void {
    this.getPatients();
    this.currentMenuItem = Object.assign({}, this.menuServiceForm.value);
  }


  onClose() {
    this.dialogRef.close();
  }

  getPatients() {
    this.patientService.getPatients().subscribe(
      (data: Patient[]) => {
        this.patientService.patientList = [...data];
        this.patientList = [...data];
      });
  }

  limitsChanged() {
    let factor = Number(this.menuServiceForm.value.energyLimit / this.currentMenuItem.energyLimit);

    this.menuServiceForm.get("proteinsLimit").setValue(Number(factor * this.currentMenuItem.proteinsLimit).toFixed(2));
    this.menuServiceForm.get("fatsLimit").setValue(Number(factor * this.currentMenuItem.fatsLimit).toFixed(2));
    this.menuServiceForm.get("carbohydratesLimit").setValue(Number(factor * this.currentMenuItem.carbohydratesLimit).toFixed(2));
  }

  menuLength() {
    let length = this.menuServiceForm.value.weekMenusCount;
    if (length == 1) return length + " tydzień";
    else if (length < 5) return length + " tygodnie";
    else return length + " tygodni";
  }

  onSubmit() {
    if (!this.menuService.form.valid) { return; }

    this.menuService.copyMenu(this.menuServiceForm.value).subscribe(result => {
      this.notificationService.success(":: Skopiowano pomyślnie! ::");
    }, error => {
      this.notificationService.warn(":: Wystąpił problem z kopiowaniem! ::");
    }, () => {
      this.onClose();
    });
  }

}
