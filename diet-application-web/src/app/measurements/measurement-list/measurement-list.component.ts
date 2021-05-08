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
              patientId: item.patientId,
              measurementDate: item.measurementDate,
              bodyWeight: item.bodyWeight,
              breast: item.breast,
              underBreast: item.underBreast,
              waist: item.waist,
              abdominal: item.abdominal,
              hipBones: item.hipBones,
              hips: item.hips,
              thighWidest: item.thighWidest,
              thighNarrowest: item.thighNarrowest,
              calf: item.calf,
              chest: item.chest,
              arm: item.arm,
            }
          });

          for (let element of array) {
            if (element.measurementDate != null) {
              let dateFormat = new Date(element.measurementDate);
              let date = dateFormat.getDate();
              let month = dateFormat.getMonth();
              let year = dateFormat.getFullYear();
              element.measurementDate = date + "/" + (month + 1) + "/" + year;
            }
          }

          if (array.length > 0) {
            this.listData = new MatTableDataSource(array);
          } else {
            this.listData = new MatTableDataSource();
          }

          this.showTable = true;
          this.objectKeys = Object.keys(this.service.form.value);
        });
  }

  onSubmit() {
    if (this.service.form.valid) {
      this.service.form.get("patientId").patchValue(this.service.patientId);
      this.service.insertMeasurement(this.service.form.value).subscribe();
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
