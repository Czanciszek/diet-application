import {Component, OnInit, ViewChild} from '@angular/core';
import {MenuService} from "../../service/menu.service";
import {ActivatedRoute, Router} from "@angular/router";
import {FileService} from "../../service/file.service";
import {MeasurementService} from "../../service/measurement.service";
import {NotificationService} from "../../service/notification.service";
import {Menu} from "../../model/menu";
import {MatSort} from "@angular/material/sort";
import {MatPaginator} from "@angular/material/paginator";
import {MatTableDataSource} from "@angular/material/table";
import {Measurement} from "../../model/measurement";
import {MatDialog} from "@angular/material/dialog";
import {MenuAddComponent} from "../menu-add/menu-add.component";
import {GenerateMenuPanelComponent} from "../generate-menu-panel/generate-menu-panel.component";
import {CopyMenuPanelComponent} from "../copy-menu-panel/copy-menu-panel.component";

@Component({
  selector: 'app-menu-list',
  templateUrl: './menu-list.component.html',
  styleUrls: ['./menu-list.component.css']
})
export class MenuListComponent implements OnInit {

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private menuService: MenuService,
    private dialog: MatDialog,
    private measurementService: MeasurementService,
    private notificationService: NotificationService,
    private fileService: FileService
  ) { }

  displayedColumns: string[] = ['order', 'dateRange', 'weekCount', 'actions'];
  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  listData: MatTableDataSource<any>;

  measurements = Array<{
    measurementId: string,
    measurementDate: string,
  }>();

  measurement: any;
  dataLoaded = false;

  ngOnInit(): void {
    this.getMenuList();
  }

  getMenuList() {
    let patientId = this.route.snapshot.paramMap.get("patient_id");

    this.menuService.getMenusByPatientId(patientId)
      .subscribe(
        (data: Menu[]) => {
          var menuList: Menu[] = [...data].sort( (m1, m2) => {
            return m1.id > m2.id ? 1 : -1;
          });
          menuList.forEach(
            (value, index) => {
            value.order = index + 1;
          });
          this.listData = new MatTableDataSource(menuList);

          this.listData.sort = this.sort;
          this.listData.paginator = this.paginator;
          this.getMeasurementDates();
        }
      );
  }

  getMeasurementDates() {
    for (let data of this.listData.data) {
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
    else if (count < 5) return " tygodnie";
    else return " tygodni";
  }

  onEnterMenuDetails(menuId) {
    this.router.navigate(["/menu/" + menuId]);
  }

  onCreate() {
    this.menuService.initializeFormGroup();
    this.openDialog();
  }

  onEditMenu(menu) {
    const editMenuObject = Object.assign({}, menu);
    editMenuObject.weekCount = menu.weekMealList.length;
    delete editMenuObject.weekMealList;
    delete editMenuObject.dayMealTypeList;
    delete editMenuObject.endDate;
    this.menuService.populateForm(editMenuObject);

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

  openGeneratingMenuDialog(menu) {
    this.fileService.initializeFormGroup();
    let dialogRef = this.dialog.open(GenerateMenuPanelComponent, {
      disableClose: true,
      autoFocus: true,
      width: "90%"
    });

    dialogRef.componentInstance.menuId = menu.id;
    dialogRef.componentInstance.recommendations = menu.recommendations;

    dialogRef.afterClosed().subscribe( result => {
      this.getMenuList();
    });
  }

  openCopyMenuDialog(menuItem) {
    this.fileService.initializeFormGroup();
    let dialogRef = this.dialog.open(CopyMenuPanelComponent, {
      disableClose: true,
      autoFocus: true,
      width: "90%"
    });

    dialogRef.componentInstance.menuItem = menuItem;

    dialogRef.afterClosed().subscribe( result => {
      this.getMenuList();
    });
  }

  onDeleteMenuButtonClick(menuId) {
    if (!confirm("Na pewno chcesz usunąć ten jadłospis?")) {
      return;
    }

    this.menuService.deleteMenu(menuId).subscribe(
      result => {
        this.notificationService.warn(":: Usunięto pomyślnie! ::");
      }, error => {
        this.notificationService.error(":: Wystąpił błąd podczas usuwania! ::");
      }, () => {
        this.ngOnInit();
    });

  }

}
