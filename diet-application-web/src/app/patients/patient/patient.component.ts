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
    private service: PatientService,
    private measurementService: MeasurementService,
    private dialog: MatDialog,
  ) { }

  ngOnInit(): void {
    let patientId = this.route.snapshot.paramMap.get("patient_id");
    this.service.getPatientById(patientId);
  }

  onEnterMeasurements(patientId) {
    this.measurementService.patientId = patientId;
    this.openDialog();
  }

  onEnterMenus(patientId) {
    this.measurementService.patientId = patientId;
    this.router.navigate(["/patients/" + patientId + "/menus"]);
  }

  openDialog() {
    let dialogRef = this.dialog.open(MeasurementListComponent, {
      //disableClose: true,
      autoFocus: true,
      width: "90%"
    });

    dialogRef.afterClosed().subscribe( result => {
      this.ngOnInit();
    });
  }
}
