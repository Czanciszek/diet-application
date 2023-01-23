import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import {NotificationService} from "../../service/notification.service";
import {MatDialog, MatDialogRef} from "@angular/material/dialog";
import {MenuService} from "../../service/menu.service";
import {MeasurementService} from "../../service/measurement.service";
import {Measurement} from "../../model/measurement";
import {PatientService} from "../../service/patient.service";
import {ProductService} from "../../service/product.service";
import {ReplaceProductPanelComponent} from "../replace-product-panel/replace-product-panel.component";
import {FOOD_TYPES} from "../../model/helpers/foodTypes";

@Component({
  selector: 'app-menu-add',
  templateUrl: './menu-add.component.html',
  styleUrls: ['./menu-add.component.css']
})
export class MenuAddComponent implements OnInit {

  constructor(
    private menuService: MenuService,
    private measurementService: MeasurementService,
    private patientService: PatientService,
    private productService: ProductService,
    private notificationService: NotificationService,
    private dialog: MatDialog,
    public dialogRef: MatDialogRef<MenuAddComponent>
  ) { }

  menuServiceForm = this.menuService.form;

  customLimits = true;
  allowEdit = true;

  foodTypes = FOOD_TYPES;

  measurementDates = [];

  currentPatientId = "";

  ngOnInit() {
    this.currentPatientId = this.patientService.form.get("id").value;
    this.getMeasurementList(this.currentPatientId);

    if (this.menuService.form.value.id != null) {
      this.allowEdit = false;
      this.checkValues();
    }
  }

  onClear() {
    this.menuService.form.reset();
    this.menuService.initializeFormGroup();
  }

  onClose() {
    this.dialogRef.close();
    this.onClear();
  }

  getMeasurementList(patientId) {
    this.measurementService.getMeasurementsByPatientId(patientId)
      .subscribe(
        (data: Measurement[]) => {
          const measurementList = { ...data};
          this.prepareMeasurementDates(measurementList);
        }
      );
  }

  prepareMeasurementDates(measurementList) {
    if (measurementList == null) { return; }
    for (let index in measurementList) {
      if (measurementList[index].measurementDate == null) { continue; }
      const patientWeight = measurementList[index].bodyWeight;
      const dateFormat = new Date(measurementList[index].measurementDate);
      const dateString = dateFormat.getDate() + "/"
        + (dateFormat.getMonth() + 1) + "/" + dateFormat.getFullYear();
        this.measurementDates.push({ id: measurementList[index].id, date: dateString, weight: patientWeight });
    }
  }

  getMeasurementDates() {
    return this.measurementDates;
  }

  onSubmit() {
    if (!this.menuService.form.valid) { return; }

    if (!this.checkLimits()) {
      this.notificationService.error(":: Suma % musi być równa 100! ::");
      return;
    }

    this.menuService.form.get("patientId").setValue(this.currentPatientId);

    if (!this.menuService.form.get('id').value) {
      this.menuService.insertMenu(this.menuService.form.value).subscribe( result => {
        this.notificationService.success(":: Pomyślnie stworzono jadłospis! ::");
        this.onClose();
      }, error => {
        this.notificationService.warn(":: Wystąpił problem z tworzeniem jadłospisu! ::");
      });
    } else {
      this.menuService.updateMenu(this.menuService.form.value).subscribe(result => {
        this.notificationService.success(":: Jadłospis zaktualizowano pomyślnie! ::");
        this.onClose();
      }, error => {
        this.notificationService.warn(":: Wystąpił problem z aktualizacją jadłospisu! ::");
      });
    }
  }

  limitPropertyChanged(measurement, activityLevel) {
    if (activityLevel == "" || measurement == "") {
      return;
    }
    let ppm = this.calculatePPM(measurement);
    let cpm = this.calculateCPM(ppm, activityLevel);

    this.menuService.form.get("energyLimit").setValue(cpm);
  }

  getAge(date: any) {
    let ageDifMs = Date.now() - new Date(date).getMilliseconds()
    let ageDate = new Date(ageDifMs);
    return Math.abs(ageDate.getUTCFullYear() - 1970);
  }

  calculatePPM(measurement) {
    let sex = this.patientService.form.get("sex").value;
    let age = this.getAge(this.patientService.form.get("birthDate").value);
    let bodyHeight = this.patientService.form.get("bodyHeight").value;
    let bodyWeight = measurement.split("&")[1];

    let ppmBase = (sex) ? 655.1 : 66.5;
    let weightFactor = (sex) ? 9.563 : 13.75;
    let heightFactor = (sex) ? 1.85 : 5.003;
    let ageFactor = (sex) ? 4.676 : 6.775;

    let ppm = ppmBase +
      weightFactor * bodyWeight +
      heightFactor * bodyHeight +
      ageFactor * age;

    return Math.floor(ppm);
  }

  calculateCPM(ppm, activityLevel) {
    return Math.floor(ppm * activityLevel);
  }

  /////////////////////////////////////////////////////////////////////////

  @ViewChild('proteinsPercentage') proteinsPercentage: ElementRef;
  @ViewChild('fatsPercentage') fatsPercentage: ElementRef;
  @ViewChild('carbohydratesPercentage') carbohydratesPercentage: ElementRef;

  customLimitsOptionChanged(event) {
    this.customLimits = event.source.checked;
    this.menuService.form.get("energyLimit").setValue(null);
  }

  limitsChanged() {
    let proteinsPercentage = Number(this.proteinsPercentage.nativeElement.value);
    if (proteinsPercentage > 0) {
      this.percentageChanged(proteinsPercentage, true, false, 'proteins');
    }

    let fatsPercentage = Number(this.fatsPercentage.nativeElement.value);
    if (fatsPercentage > 0) {
      this.percentageChanged(fatsPercentage, true, false, 'fats');
    }

    let carbohydratesPercentage = Number(this.carbohydratesPercentage.nativeElement.value);
    if (carbohydratesPercentage > 0) {
      this.percentageChanged(carbohydratesPercentage, true, false, 'carbohydrates');
    }
  }

  percentageChanged(newValue: number, needToCheckValue: boolean, needToCheckPercentages: boolean, type: string) {
    newValue = Math.max(0, Math.min(100, newValue));

    let factor = 4;
    if (type == "fats") {
      factor = 9;
      this.fatsPercentage.nativeElement.value = newValue;
    } else if (type == "carbohydrates") {
      this.carbohydratesPercentage.nativeElement.value = newValue;
    } else if (type == "proteins") {
      this.proteinsPercentage.nativeElement.value = newValue;
    }

    let limit = Number(this.menuService.form.value.energyLimit);
    if (limit > 0) {
      if (needToCheckValue) {
        let grams =  Number((limit * newValue / 100 / factor).toFixed(1));
        this.valueChanged(grams, false, type);
      }

      if (needToCheckPercentages) {
        this.checkPercentages(type);
      }
    }
  }

  valueChanged(newValue: number, needToCheckPercentage: boolean, type: string) {
    let limit = Number(this.menuService.form.value.energyLimit);
    newValue = Math.max(0, Math.min(limit, newValue));

    let factor = 4;
    if (type == "fats") {
      factor = 9;
      this.menuService.form.get("fatsLimit").setValue(newValue);
    } else if (type == "carbohydrates") {
      this.menuService.form.get("carbohydratesLimit").setValue(newValue);
    } else if (type == "proteins") {
      this.menuService.form.get("proteinsLimit").setValue(newValue);
    }

    if (limit > 0 && needToCheckPercentage) {
      let percentage = Number((newValue / limit * 100 * factor).toFixed(2));
      this.percentageChanged(percentage, false, true, type);
    }
  }

  checkPercentages(changedType: string) {

    let proteinsPercentage = Number(this.proteinsPercentage.nativeElement.value);
    let fatsPercentage = Number(this.fatsPercentage.nativeElement.value);
    let carbohydratesPercentage = Number(this.carbohydratesPercentage.nativeElement.value);

    if (proteinsPercentage == 100) {
      this.percentageChanged(0, true, false, 'fats');
      this.percentageChanged(0, true, false, 'carbohydrates');
    } else if (fatsPercentage == 100) {
      this.percentageChanged(0, true, false, 'proteins');
      this.percentageChanged(0, true, false, 'carbohydrates');
    } else if (carbohydratesPercentage == 100) {
      this.percentageChanged(0, true, false, 'proteins');
      this.percentageChanged(0, true, false, 'fats');
    } else {
      if ( (changedType == "fats" || changedType == "proteins") && fatsPercentage > 0 && proteinsPercentage > 0) {
        let carbohydratesPercentage = (100 - proteinsPercentage - fatsPercentage);
        this.percentageChanged( Math.max(carbohydratesPercentage, 0), true, false, 'carbohydrates');
      } else if (changedType == "carbohydrates" && carbohydratesPercentage > 0 && proteinsPercentage > 0) {
        let fatsPercentage = (100 - proteinsPercentage - carbohydratesPercentage);
        this.percentageChanged( Math.max(fatsPercentage, 0), true, false, 'fats');
      }
    }
  }

  checkValues() {
    setTimeout(() => {
      this.valueChanged(Number(this.menuService.form.value.proteinsLimit), true, "proteins");
      this.valueChanged(Number(this.menuService.form.value.fatsLimit), true, "fats");
      let percentage = Number(100 - Number(this.proteinsPercentage.nativeElement.value) - Number(this.fatsPercentage.nativeElement.value) ).toFixed(2);
      this.carbohydratesPercentage.nativeElement.value = percentage;
    });
  }

  checkLimits() {
    let proteinsPercentage = Number(this.proteinsPercentage.nativeElement.value);
    let fatsPercentage = Number(this.fatsPercentage.nativeElement.value);
    let carbohydratesPercentage = Number(this.carbohydratesPercentage.nativeElement.value);

    let summary = proteinsPercentage + fatsPercentage + carbohydratesPercentage;
    return summary == 100;
  }

    onReplaceProductsClick() {

      let dialogRef = this.dialog.open(ReplaceProductPanelComponent, {
        autoFocus: true,
        width: "90%",
        height: "70%"
      });

      dialogRef.afterClosed().subscribe( (result) => {
        if (result == null) return;
        this.replaceProducts(result);
      });

    }

    replaceProducts(products) {
      let menuId = this.menuServiceForm.get("id").value;

      this.menuService
        .replaceProductInMenu(menuId, products)
        .subscribe(
          (response) => {
            this.notificationService.success(":: Produkt zaktualizowano pomyślnie! ::");
          }, error => {
           this.notificationService.error(":: Wystąpił błąd podczas aktualizacji produktu! ::");
        });
    }
}
