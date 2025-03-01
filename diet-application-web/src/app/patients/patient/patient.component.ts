import { Component, OnInit } from '@angular/core';
import { PatientService } from "../../service/patient.service";
import { NotificationService } from "../../service/notification.service";
import { ActivatedRoute, Router } from "@angular/router";
import { MatDialog } from "@angular/material/dialog";
import { MeasurementListComponent } from "../../measurements/measurement-list/measurement-list.component";
import { MeasurementService } from "../../service/measurement.service";

@Component({
    selector: 'app-patient',
    templateUrl: './patient.component.html',
    styleUrls: ['./patient.component.css'],
    standalone: false
})
export class PatientComponent implements OnInit {

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private patientService: PatientService,
    private measurementService: MeasurementService,
    private notificationService: NotificationService,
    private dialog: MatDialog,
  ) { }

  patientServiceForm = this.patientService.form;

  ngOnInit(): void {
    let patientId = this.route.snapshot.paramMap.get("patient_id");
    this.patientService.getPatientById(patientId).subscribe(
      patient => {
        this.patientService.populateForm(patient);
      }, error => {
        this.handleError(error);
      })
  }

  onEnterMeasurements() {
    this.openDialog();
  }

  openDialog() {
    let dialogRef = this.dialog.open(MeasurementListComponent, {
      autoFocus: true,
      width: "90%"
    });

    dialogRef.componentInstance.patientId = this.patientServiceForm.value.id;
    dialogRef.componentInstance.measurements = this.patientServiceForm.value.measurements;

    dialogRef.afterClosed().subscribe(result => {
      this.ngOnInit();
    });
  }

  handleError(error) {
    if (error.status === 403) {
      this.notificationService.error(":: Nie masz odpowiednich uprawnień! ::");
    } else if (error.status === 404) {
      this.notificationService.error(":: Nie znaleziono takiego pacjenta! ::");
    } else {
      this.notificationService.error(":: Wystąpił nieznany błąd! ::");
    }

    this.router.navigate(["/patients"]);
  }
}
