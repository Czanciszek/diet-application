import {Component, OnInit} from '@angular/core';
import {MatDialogRef} from "@angular/material/dialog";
import {MeasurementService} from "../../service/measurement.service";
import {MatTableDataSource} from "@angular/material/table";

@Component({
  selector: 'app-measurement-list',
  templateUrl: './measurement-list.component.html',
  styleUrls: ['./measurement-list.component.css']
})
export class MeasurementListComponent implements OnInit {

  constructor(
    private service: MeasurementService,
    private dialogRef: MatDialogRef<MeasurementListComponent>
  ) { }

  startDate = new Date();

  listData: MatTableDataSource<any>;
  showTable = false;
  objectKeys: any[];

  showNewMeasurement = false;

  ngOnInit(): void {
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

          for (let element of array) {
            if (element.measurementDate != null) {
              var dateFormat = new Date(element.measurementDate);
              var date = dateFormat.getDate();
              var month = dateFormat.getMonth();
              var year = dateFormat.getFullYear();
              element.measurementDate = date + "/" + (month + 1) + "/" + year;
            }
          }

          this.listData = new MatTableDataSource(array);
          this.showTable = true;
          this.objectKeys = Object.keys(this.listData.data["0"]);
        });
  }

  onSubmit() {
    if (this.service.form.valid) {
        this.service.insertMeasurement(this.service.form.value, this.service.patientId).subscribe();
        this.showNewMeasurement = false;
        this.onClear();
        this.ngOnInit();
    }
  }

  onClear() {
    this.service.form.reset();
    this.service.initializeFormGroup();
  }

  onClose() {
    this.onClear();
    this.dialogRef.close();
  }

  addNewMeasurement() {
    this.showNewMeasurement = true;
  }

}