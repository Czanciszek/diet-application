import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from "@angular/material/dialog";
import { MeasurementService } from "../../service/measurement.service";
import { MatTableDataSource } from "@angular/material/table";
import { PatientService } from "../../service/patient.service";
import { Measurement } from "../../model/measurement";

@Component({
  selector: 'app-measurement-list',
  templateUrl: './measurement-list.component.html',
  styleUrls: ['./measurement-list.component.css']
})
export class MeasurementListComponent implements OnInit {

  constructor(
    private measurementService: MeasurementService,
    private patientService: PatientService,
    private dialogRef: MatDialogRef<MeasurementListComponent>
  ) { }

  public patientId: string;
  public measurements: Measurement[];

  measurementForm = this.measurementService.form;
  measurementKeywords = this.measurementService.measurementKeywords;

  startDate = new Date();

  listData: MatTableDataSource<Measurement>;
  objectKeys: any[];

  showNewMeasurement = false;

  ngOnInit(): void {
    this.getPatientsMeasurements();
  }

  getPatientsMeasurements() {

    for (let element of this.measurements) {
      if (element.measurementDate == null) {
        continue;
      }

      let dateFormat = new Date(element.measurementDate);
      let date = dateFormat.getDate();
      let month = dateFormat.getMonth();
      let year = dateFormat.getFullYear();
      element.displayDate = date + "/" + (month + 1) + "/" + year;
    }

    if (this.measurements.length > 0) {
      this.listData = new MatTableDataSource(this.measurements);
    } else {
      this.listData = new MatTableDataSource();
    }

    this.objectKeys = Object.keys(this.measurementService.form.value);
  }

  onSubmit() {
    if (!this.measurementService.form.valid) {
      return;
    }

    const measurementDate = new Date(this.measurementForm.value.displayDate);
    this.measurementForm.get("measurementDate").patchValue(measurementDate);
    this.measurementForm.get("patientId").patchValue(this.patientId);
    this.showNewMeasurement = false;
    this.patientService.insertMeasurement(this.measurementForm.value)
      .subscribe((data: Measurement) => {
        this.measurements.push(data);
        this.getPatientsMeasurements();
        this.onClear();
      });
  }

  onClear() {
    this.measurementService.initializeFormGroup();
  }

  onClose() {
    this.dialogRef.close();
  }

  addNewMeasurement() {
    this.showNewMeasurement = true;
  }

}
