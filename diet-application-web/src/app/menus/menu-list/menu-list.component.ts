import {Component, OnInit} from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {NotificationService} from "../../service/notification.service";
import {MenuService} from "../../service/menu.service";
import {MatTableDataSource} from "@angular/material/table";
import {ActivatedRoute, Router} from "@angular/router";
import {MeasurementService} from "../../service/measurement.service";

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
    private measurementService: MeasurementService,
  ) { }

  listData: MatTableDataSource<any>;
  listLoaded = false;

  menuListItems = Array<{
    menuId: string,
    dateRange: string,
    weekCount: string,
    measurementDate: string
  }>();

  ngOnInit(): void {
    let patientId = this.route.snapshot.paramMap.get("patient_id");
    this.service.getMenusByPatientId(patientId).subscribe(
      list => {
        let array = list.map(item => {
          return {
            id: item.id,
            header: item.header,
            primaryImageId: item.primaryImageId,
            type: item.type,
            name: item.name,
            measurementDocRef: item.measurementDocRef,
            patientDocRef: item.patientDocRef,
            weekMealList: item.weekMealList,
            mealTypes: item.mealTypes,
            startDate: item.startDate,
            endDate: item.endDate
          };
        });
        this.listData = array;
        this.initListItems();
      });
  }

  async initListItems() {
    // @ts-ignore
    for (let item of this.listData) {
      let menuId = item.id;
      let dateRange = this.dateTimeParser(item);
      let weekCount = this.weekCount(item.weekMealList.length);
      var measurementDate = null;
      if (item.measurementDocRef != null) {
        const date = await this.getMeasurementDate(item.measurementDocRef.id);
        measurementDate = new Date(date).toDateString();
      }

      if (measurementDate == null) {
        measurementDate = "Nie wybrano";
      }

      let menuItem = {
        menuId: menuId,
        dateRange: dateRange,
        weekCount: weekCount,
        measurementDate: measurementDate
      };
      this.menuListItems.push(menuItem);
    }
    this.listLoaded = true;
  }

   async getMeasurementDate(measurementId) {
    let measurement = await this.measurementService.getMeasurementsById(measurementId).toPromise();
    return measurement.measurementDate;
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

  openMenuDetails(menuId) {
    this.router.navigate(["/menu/" + menuId]);
  }
}
