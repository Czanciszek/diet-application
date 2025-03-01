import { Component, OnInit } from '@angular/core';
import { NotificationService } from "../../service/notification.service";
import { MatDialogRef } from "@angular/material/dialog";
import { PatientService } from "../../service/patient.service";
import { ProductService } from "../../service/product.service";
import { ALLERGEN_TYPES } from "../../model/helpers/allergenTypes";

@Component({
    selector: 'app-patient-edit',
    templateUrl: './patient-edit.component.html',
    styleUrls: ['./patient-edit.component.css'],
    standalone: false
})
export class PatientEditComponent implements OnInit {

  startDate = new Date(1985, 0, 1);

  allergens_list = ALLERGEN_TYPES;
  categories = [];

  constructor(
    private patientService: PatientService,
    private productService: ProductService,
    private notificationService: NotificationService,
    public dialogRef: MatDialogRef<PatientEditComponent>
  ) { }

  patientServiceForm = this.patientService.form;

  ngOnInit(): void {
    let response = this.productService.getCategories();
    response.subscribe(data => {
      for (const key of Object.values(data)) {
        this.categories.push(key.subcategory);
      }
    });
  }

  onClear() {
    this.patientService.form.reset();
    this.patientService.initializeFormGroup();
  }

  onSubmit() {
    if (!this.patientService.form.valid) {
      return;
    }

    if (!this.patientService.form.get('id').value) {
      this.patientService
        .insertPatient(this.patientService.form.value)
        .subscribe(
          patient => {
            this.notificationService.success(":: Pacjent dodany pomyślnie! ::");
          }, error => {
            this.notificationService.error(":: Wystąpił błąd podczas dodawania pacjenta! ::");
          }, () => {
            this.onClose();
          }
        );
    } else {
      this.patientService
        .updatePatient(this.patientService.form.value)
        .subscribe(
          patient => {
            this.notificationService.success(":: Pacjent zaktualizowany pomyślnie! ::");
          }, error => {
            this.notificationService.error(":: Wystąpił błąd podczas aktualizacji pacjenta! ::");
          }, () => {
            this.onClose();
          }
        );
    }
  }

  onClose() {
    this.onClear();
    this.dialogRef.close();
  }
}
