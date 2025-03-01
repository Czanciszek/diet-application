import { Component, OnInit, ViewChild } from '@angular/core';
import { PatientService } from "../../service/patient.service";
import { MatDialog } from "@angular/material/dialog";
import { NotificationService } from "../../service/notification.service";
import { MatTableDataSource } from "@angular/material/table";
import { MatSort } from "@angular/material/sort";
import { MatPaginator } from "@angular/material/paginator";
import { Router } from "@angular/router";
import { PatientEditComponent } from "../patient-edit/patient-edit.component";
import { Patient } from "../../model/patient";

@Component({
    selector: 'app-patient-list',
    templateUrl: './patient-list.component.html',
    styleUrls: ['./patient-list.component.css'],
    standalone: false
})
export class PatientListComponent implements OnInit {

  constructor(
    private router: Router,
    private patientService: PatientService,
    private dialog: MatDialog,
    private notificationService: NotificationService
  ) { }

  listData: MatTableDataSource<any>;
  displayedColumns: string[] = ['name', 'surname', 'email', 'numberPhone', 'actions'];
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  searchKey: string;

  ngOnInit(): void {
    this.getPatients();
  }

  getPatients() {
    this.patientService.getPatients().subscribe(
      (data: Patient[]) => {

        var patientList: Patient[] = [...data].sort((p1, p2) => {
          if (p1.surname == p2.surname) return 0;
          return p1.surname > p2.surname ? 1 : -1;
        });
        this.patientService.patientList = patientList;
        this.listData = new MatTableDataSource(this.patientService.patientList);
        this.listData.sort = this.sort;
        this.listData.paginator = this.paginator;

        this.listData.filterPredicate = (data: Patient, filter: string) => {
          return data.name == null ||
            data.name.toLowerCase().includes(filter) ||
            data.email.toLowerCase().includes(filter) ||
            data.numberPhone.toLowerCase().includes(filter);
        };
      });
  }

  onSearchClear() {
    this.searchKey = "";
    this.applyFilter();
  }

  applyFilter() {
    this.listData.filter = this.searchKey.trim().toLowerCase();
  }

  onCreate() {
    this.patientService.initializeFormGroup();
    this.openDialog();
  }

  onEnterDetails(patientId) {
    this.router.navigate(["/patients/" + patientId]);
  }

  onEdit(patient) {
    this.patientService.populateForm(patient);
    this.openDialog();
  }

  openDialog() {
    let dialogRef = this.dialog.open(PatientEditComponent, {
      disableClose: true,
      autoFocus: true,
      width: "90%"
    });

    dialogRef.afterClosed().subscribe(result => {
      this.ngOnInit();
    });
  }

  onDelete(patientId) {
    if (!confirm("Na pewno usunąć tego pacjenta?")) {
      return;
    }

    this.patientService.deletePatient(patientId).subscribe(
      result => {
        this.notificationService.warn(":: Pomyślnie usunięto! ::");
      }, error => {
        this.notificationService.error(":: Wystąpił błąd podczas usuwania! ::");
      }, () => {
        this.getPatients();
      }
    );
  }

}
