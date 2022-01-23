import {Component, OnInit, ViewChild} from '@angular/core';
import {MenuService} from "../../service/menu.service";
import {ActivatedRoute, Router} from "@angular/router";
import {FileService} from "../../service/file.service";
import {MeasurementService} from "../../service/measurement.service";
import {Menu} from "../../model/menu";
import {MatSort} from "@angular/material/sort";
import {MatPaginator} from "@angular/material/paginator";
import {Measurement} from "../../model/measurement";
import {MatDialog} from "@angular/material/dialog";
import {MenuAddComponent} from "../menu-add/menu-add.component";

@Component({
  selector: 'app-menu-list',
  templateUrl: './menu-list.component.html',
  styleUrls: ['./menu-list.component.css']
})
export class MenuListComponent implements OnInit {

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private service: MenuService,
    private dialog: MatDialog,
    private measurementService: MeasurementService,
    private fileService: FileService
  ) { }

  displayedColumns: string[] = ['dateRange', 'weekCount', 'actions'];
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  searchKey: string;

  listData: any;

  measurements = Array<{
    measurementId: string,
    measurementDate: string,
  }>();

  measurement: any;
  dataLoaded = false;

  ngOnInit(): void {
    let patientId = this.route.snapshot.paramMap.get("patient_id");
    this.service.getMenusByPatientId(patientId)
      .subscribe(
        (data: Menu[]) => {
          this.listData = [ ...data];
          this.listData.sort = this.sort;
          this.listData.paginator = this.paginator;
          this.getMeasurementDates();
        }
      );
  }

  onSearchClear() {
    this.searchKey = "";
    this.applyFilter();
  }

  applyFilter() {
    this.listData.filter = this.searchKey.trim().toLowerCase();
  }

  getMeasurementDates() {
    for (let data of this.listData) {
      if (data.measurementId != null) {
        this.getMeasurementById(data.measurementId);
      }
    }
    this.dataLoaded = true;
  }

  async getMeasurementById(measurementId) {
    const measurement = <Measurement> await this.measurementService.getMeasurementsById(measurementId).toPromise();
    const date = new Date(measurement.measurementDate).toDateString();
    this.measurements.push({measurementId: measurement.id, measurementDate: date});
  }

  dateTimeParser(menu) {
    let startDate = new Date(menu.startDate).toDateString();
    let endDate = new Date(menu.endDate).toDateString();

    return startDate + " - " + endDate;
  }

  weekCount(weekMealCount) {
    let describeText = this.getDescribingText(weekMealCount);
    return weekMealCount + describeText;
  }

  getDescribingText(count) {
    if (count == 1) return " tydzień";
    else if (count >= 2 && count <= 4) return " tygodnie";
    else return " tygodni";
  }

  onEnterMenuDetails(menuId) {
    this.router.navigate(["/menu/" + menuId]);
  }

  onGeneratePDFButtonClick(menuId) {
    this.fileService.getPdfFile(menuId);
  }

  onCreate() {
    this.service.initializeFormGroup();
    this.openDialog();
  }

  openDialog() {
    let dialogRef = this.dialog.open(MenuAddComponent, {
      disableClose: true,
      autoFocus: true,
      width: "90%"
    });

    dialogRef.afterClosed().subscribe( result => {
      this.ngOnInit();
    });
  }

}
