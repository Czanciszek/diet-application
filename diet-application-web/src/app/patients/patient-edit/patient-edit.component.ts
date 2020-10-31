import { Component, OnInit } from '@angular/core';
import {NotificationService} from "../../service/notification.service";
import {MatDialogRef} from "@angular/material/dialog";
import {PatientService} from "../../service/patient.service";
import {FormControl} from "@angular/forms";

@Component({
  selector: 'app-patient-edit',
  templateUrl: './patient-edit.component.html',
  styleUrls: ['./patient-edit.component.css'],

})
export class PatientEditComponent implements OnInit {

  startDate = new Date(1985, 0, 1);
  date = new FormControl(new Date());

  constructor(
    private service: PatientService,
    private notificationService: NotificationService,
    public dialogRef: MatDialogRef<PatientEditComponent>
  ) { }

  ngOnInit(): void {
  }

  onClear() {
    this.service.form.reset();
    this.service.initializeFormGroup();
  }

  onSubmit() {
    if (this.service.form.valid) {
      if (!this.service.form.get('id').value) {
        this.service.insertPatient(this.service.form.value).subscribe();
        this.notificationService.success(":: Patient created successfully! ::");
      } else {
        this.service.updatePatient(this.service.form.value).subscribe();
        this.notificationService.success(":: Patient updated successfully! ::");
      }
      this.onClose();
    }
  }

  onClose() {
    this.onClear();
    this.dialogRef.close();
  }
}
