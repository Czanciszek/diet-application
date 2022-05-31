import { Component, OnInit } from '@angular/core';
import {NotificationService} from "../../service/notification.service";
import {MatDialogRef} from "@angular/material/dialog";
import {PatientService} from "../../service/patient.service";
import {FormControl} from "@angular/forms";
import {ProductService} from "../../service/product.service";

@Component({
  selector: 'app-patient-edit',
  templateUrl: './patient-edit.component.html',
  styleUrls: ['./patient-edit.component.css'],

})
export class PatientEditComponent implements OnInit {

  startDate = new Date(1985, 0, 1);
  date = new FormControl(new Date());

  allergenTypes: any = [
    { id: "STARCH", value: "Skrobia"},
    { id: "LACTOSE", value: "Laktoza"},
    { id: "GLUTEN", value: "Gluten"},
  ];

  unfilteredCategories = [];
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
        this.unfilteredCategories.push(key.subcategory);
      }
      this.categories = this.unfilteredCategories;
    });
  }

  onClear() {
    this.patientService.form.reset();
    this.patientService.initializeFormGroup();
  }

  onSubmit() {
    if (this.patientService.form.valid) {
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
  }

  onClose() {
    this.onClear();
    this.dialogRef.close();
  }

  public filterOptions(filter: string): void {
    this.categories = this.unfilteredCategories.filter(x => x.toLowerCase().includes(filter.toLowerCase()));
  }

}
