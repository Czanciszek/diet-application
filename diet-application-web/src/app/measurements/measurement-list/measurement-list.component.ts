import { Component, OnInit } from '@angular/core';
import { MatLegacyDialogRef as MatDialogRef } from "@angular/material/legacy-dialog";
import { MeasurementService } from "../../service/measurement.service";
import { MatLegacyTableDataSource as MatTableDataSource } from "@angular/material/legacy-table";
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
