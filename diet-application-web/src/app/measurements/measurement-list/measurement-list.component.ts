import { Component, OnInit } from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {NotificationService} from "../../service/notification.service";
import {MatTableDataSource} from "@angular/material/table";
import {MeasurementService} from "../../service/measurement.service";

@Component({
  selector: 'app-measurement-list',
  templateUrl: './measurement-list.component.html',
  styleUrls: ['./measurement-list.component.css']
})
export class MeasurementListComponent implements OnInit {

  constructor(
    private service: MeasurementService,
    private dialog: MatDialog,
    private notificationService: NotificationService
  ) { }

  listData: MatTableDataSource<any>;

  ngOnInit(): void {
    //console.log(this.service.patientId);
    this.service.getMeasurementsByPatientId(this.service.patientId)
      .subscribe(
        list => {
          let array = list.map(item => {
            return {
              id: item.id,
              header: item.header,
              primaryImageId: item.primaryImageId,
              type: item.type,
              name: item.name,
              patientDocRef: item.patientDocRef,
              measurementDate: item.measurementDate,
              bodyWeight: item.bodyWeight,
              waist: item.waist,
              abdominal: item.abdominal,
              hips: item.hips,
              thighWidest: item.thighWidest,
              calf: item.calf,
              breast: item.breast,
              underBreast: item.underBreast,
              hipBones: item.hipBones,
              thighNarrowest: item.thighNarrowest,
              chest: item.chest,
              arm: item.arm,
            }
          });
          this.listData = new MatTableDataSource(array);
          console.log(this.listData);
        });
  }

}
