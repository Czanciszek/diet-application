import {Component, OnInit, ViewChild} from '@angular/core';
import {PatientService} from "../../service/patient.service";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {NotificationService} from "../../service/notification.service";
import {MatTableDataSource} from "@angular/material/table";
import {MatSort} from "@angular/material/sort";
import {MatPaginator} from "@angular/material/paginator";

@Component({
  selector: 'app-patient-list',
  templateUrl: './patient-list.component.html',
  styleUrls: ['./patient-list.component.css']
})
export class PatientListComponent implements OnInit {

  constructor(
    private service: PatientService,
    private dialog: MatDialog,
    private notificationService: NotificationService
  ) { }

  listData: MatTableDataSource<any>;
  displayedColumns: string[] = ['name', 'email', 'bodyWeight',
    'numberPhone', 'actions'];
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  searchKey: string;

  ngOnInit(): void {
    this.service.getPatients().subscribe(
      list => {
        let array = list.map(item => {
          return {
            id: item.id,
            header: item.header,
            primaryImageId: item.primaryImageId,
            type: item.type,
            name: item.name,
            birthDate: item.birthDate,
            numberPhone: item.numberPhone,
            email: item.email,
            bodyWeight: item.bodyWeight,
            bodyHeight: item.bodyHeight,
            currentLifestyleNote: item.currentLifestyleNote,
            changedLifestyleNote: item.changedLifestyleNote,
            dietaryPurpose: item.dietaryPurpose,
          };
        });
        this.listData = new MatTableDataSource(array);
        this.listData.sort = this.sort;
        this.listData.paginator = this.paginator;
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
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.disableClose = true;
    dialogConfig.width = "90%";

   // this.dialog.open(ProductComponent, dialogConfig);
  }

  onEdit(patient) {
    this.service.populateForm(patient);
    const dialogConfig = new MatDialogConfig();
    dialogConfig.autoFocus = true;
    dialogConfig.disableClose = true;
    dialogConfig.width = "90%";

    //this.dialog.open(ProductComponent, dialogConfig);
  }

  onDelete(patientId) {
    if (confirm("Are you sure to delete this product?")) {
      //this.service.deleteProduct(productId);
      this.notificationService.warn(":: Deleted succesfully! ::");
    }
  }

}
