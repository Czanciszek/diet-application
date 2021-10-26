import {Component, OnInit, ViewChild} from '@angular/core';
import {PatientService} from "../../service/patient.service";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {NotificationService} from "../../service/notification.service";
import {MatTableDataSource} from "@angular/material/table";
import {MatSort} from "@angular/material/sort";
import {MatPaginator} from "@angular/material/paginator";
import {Router} from "@angular/router";
import {PatientEditComponent} from "../patient-edit/patient-edit.component";
import {Patient} from "../../model/patient";

@Component({
  selector: 'app-patient-list',
  templateUrl: './patient-list.component.html',
  styleUrls: ['./patient-list.component.css']
})
export class PatientListComponent implements OnInit {

  constructor(
    private router: Router,
    private service: PatientService,
    private dialog: MatDialog,
    private notificationService: NotificationService
  ) { }

  listData: MatTableDataSource<any>;
  displayedColumns: string[] = ['name', 'email', 'numberPhone', 'actions'];
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  searchKey: string;

  ngOnInit(): void {
    this.getPatients();
  }

  getPatients() {
    this.service.getPatients().subscribe(
      list => {
        let array = list.map(item => {
          return {
            id: item.id,
            name: item.name,
            birthDate: item.birthDate,
            numberPhone: item.numberPhone,
            email: item.email,
            sex: item.sex,
            bodyHeight: item.bodyHeight,
            currentLifestyleNote: item.currentLifestyleNote,
            changedLifestyleNote: item.changedLifestyleNote,
            dietaryPurpose: item.dietaryPurpose,
            allergens: item.allergens,
            unlikelyCategories: item.unlikelyCategories
          };
        });
        this.listData = new MatTableDataSource(array);
        this.listData.sort = this.sort;
        this.listData.paginator = this.paginator;

        this.listData.filterPredicate = (data: Patient, filter: string) => {
          return data == null ||
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
    this.service.initializeFormGroup();
    this.openDialog();
  }

  onEnterDetails(patientId) {
    this.router.navigate(["/patients/" + patientId]);
  }

  onEdit(patient) {
    this.service.populateForm(patient);
    this.openDialog();
  }

  openDialog() {
    let dialogRef = this.dialog.open(PatientEditComponent, {
      disableClose: true,
      autoFocus: true,
      width: "90%"
    });

    dialogRef.afterClosed().subscribe( result => {
      this.ngOnInit();
    });
  }

  onDelete(patientId) {
    if (confirm("Are you sure to delete this patient?")) {
      this.service.deletePatient(patientId).subscribe( result => {

        let listDataProduct = this.listData.data.find( x => x.id == patientId);
        let index = this.listData.data.indexOf(listDataProduct);
        this.listData.data.splice(index, 1);
        this.listData.data = this.listData.data;

        this.notificationService.warn(":: Deleted succesfully! ::");
      });

      this.getPatients();
    }
  }

}
