import { Component, OnInit } from '@angular/core';
import {PatientService} from "../../service/patient.service";
import {ActivatedRoute, Router} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {MeasurementListComponent} from "../../measurements/measurement-list/measurement-list.component";
import {MeasurementService} from "../../service/measurement.service";

@Component({
  selector: 'app-patient',
  templateUrl: './patient.component.html',
  styleUrls: ['./patient.component.css']
})
export class PatientComponent implements OnInit {

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private patientService: PatientService,
    private measurementService: MeasurementService,
    private dialog: MatDialog,
  ) { }

  patientServiceForm = this.patientService.form;

  ngOnInit(): void {
    let patientId = this.route.snapshot.paramMap.get("patient_id");
    this.patientService.getPatientById(patientId);
  }

  onEnterMeasurements() {
    this.measurementService.patientId = this.patientServiceForm.controls['id'].value;
    this.openDialog();
  }

  openDialog() {
    let dialogRef = this.dialog.open(MeasurementListComponent, {
      autoFocus: true,
      width: "90%"
    });

    dialogRef.afterClosed().subscribe( result => {
      this.ngOnInit();
    });
  }
}
